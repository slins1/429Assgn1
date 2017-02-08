// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

//import userinterface.View;
//import userinterface.ViewFactory;

/** The class containing the Patron for the Library application */
//==============================================================
public class Patron extends EntityBase implements IView
{
	private static final String myTableName = "Patron";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Patron(String patronId)
			throws InvalidPrimaryKeyException
	{
		super(myTableName);

		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one patron at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one patron. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple patrons matching id : "
						+ patronId + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedPatronData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedPatronData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedPatronData.getProperty(nextKey);

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no patron found for this user name, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No patron matching id : "
					+ patronId + " found.");
		}
	}

	// Can also be used to create a NEW Patron (if the system it is part of
	// allows for a new patron to be set up)
	//----------------------------------------------------------
	public Patron(Properties props)
	{
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);

			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();

		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	/**
	 * Verify ownership
	 */
	//----------------------------------------------------------
//	public boolean verifyOwnership(AccountHolder cust)
//	{
//		if (cust == null)
//		{
//			return false;
//		}
//		else
//		{
//			String custid = (String)cust.getState("ID");
//			String myOwnerid = (String)getState("OwnerID");
//			// DEBUG System.out.println("Account: custid: " + custid + "; ownerid: " + myOwnerid);
//
//			return (custid.equals(myOwnerid));
//		}
//	}

	//-----------------------------------------------------------------------------------
	public static int compare(Patron a, Patron b)
	{
		String aNum = (String)a.getState("patronId");
		String bNum = (String)b.getState("patronId");

		return aNum.compareTo(bNum);
	}

	//-----------------------------------------------------------------------------------
	public void update()
	{
		updateStateInDatabase();
	}

	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase()
	{
		try
		{
			if (persistentState.getProperty("patronId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("patronId",
						persistentState.getProperty("patronId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Patron data for patron number : " + persistentState.getProperty("patronId") + " updated successfully in database!";
			}
			else
			{
				Integer patronId =
						insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("patronId", "" + patronId.intValue());
				updateStatusMessage = "Patron data for new patron : " +  persistentState.getProperty("patronId")
						+ "installed successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing patron data in database!";
		}

		System.out.println("updateStateInDatabase " + updateStatusMessage);
	}


	/**
	 * This method is needed solely to enable the Patron information to be displayable in a table
	 *
	 */
	//--------------------------------------------------------------------------
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("patronId"));
		v.addElement(persistentState.getProperty("Type"));
		v.addElement(persistentState.getProperty("Balance"));
		v.addElement(persistentState.getProperty("ServiceCharge"));

		return v;
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
}