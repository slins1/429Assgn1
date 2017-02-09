// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class PatronCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Patron";

	private Vector<Patron> patrons;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public PatronCollection()
	{
		super(myTableName);
		patrons = new Vector<Patron>();
	}
	
	//----------------------------------------------------------------------------------
	public void findPatronOlderThan(String date)
	{
		String sqlSelectStatement = "SELECT * FROM " + myTableName + " WHERE dateOfBirth < "
				+ date;
		patrons = getSelectQueryResult(sqlSelectStatement);
	}
	
	//------------------------------------------------------------------------------------
	public void findPatronYoungerThan(String date)
	{
		String sqlSelectStatement = "SELECT * FROM " + myTableName + " WHERE dateOfBirth > "
				+ date;
		patrons = getSelectQueryResult(sqlSelectStatement);
	}
	
	//------------------------------------------------------------------------------------
	public void findPatronAtZip(String zip)
	{
		String sqlSelectStatement = "SELECT * FROM " + myTableName + " WHERE zip = '"
				+ zip + "'";
		patrons = getSelectQueryResult(sqlSelectStatement);
	}
	
	//------------------------------------------------------------------------------------
	public void findPatronWithNameLike(String name)
	{
		String sqlSelectStatement = "SELECT * FROM " + myTableName + " WHERE name LIKE %'"
				+ name + "'%";
		patrons = getSelectQueryResult(sqlSelectStatement);
	}

	//------------------------------------------------------------------------------------
	public Vector<Patron> getPatrons()
	{
		return patrons;
	}

	//----------------------------------------------------------------------------------
	private void addPatron(Patron a)
	{
		//accounts.add(a);
		int index = findIndexToAdd(a);
		patrons.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Patron a)
	{
		//users.add(u);
		int low=0;
		int high = patrons.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Patron midSession = patrons.elementAt(middle);

			int result = Patron.compare(a,midSession);

			if (result ==0)
			{
				return middle;
			}
			else if (result<0)
			{
				high=middle-1;
			}
			else
			{
				low=middle+1;
			}
		}
		return low;
	}


	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("stuff"))
			return patrons;
		else
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Patron retrieve(String accountNumber)
	{
		Patron retValue = null;
		for (int cnt = 0; cnt < patrons.size(); cnt++)
		{
			Patron nextPatr = patrons.elementAt(cnt);
			String nextPatNum = (String)nextPatr.getState("patronId");
			if (nextPatNum.equals(accountNumber) == true)
			{
				retValue = nextPatr;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		Scene localScene = myViews.get("PatronCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("PatronCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("PatronCollectionView", localScene);
		}
		// make the view visible by installing it into the frame
		swapToView(localScene);
		
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
