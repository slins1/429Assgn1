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

	private Vector<Patron> accounts;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public PatronCollection( Patron cust) throws
		Exception
	{
		super(myTableName);

		if (cust == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: AccountCollection.<init>: account holder information is null");
		}

		String patronId = (String)cust.getState("ID");

		if (patronId == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Data corrupted: Account Holder has no id in database", Event.FATAL);
			throw new Exception
			 ("UNEXPECTED ERROR: AccountCollection.<init>: Data corrupted: account holder has no id in repository");
		}

		String query = "SELECT * FROM " + myTableName + " WHERE (OwnerID = " + patronId + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			accounts = new Vector<Patron>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

				Patron account = new Patron(nextPatronData);

				if (account != null)
				{
					addPatron(account);
				}
			}

		}
		else
		{
			throw new InvalidPrimaryKeyException("No accounts for customer : "
				+ patronId + ". Name : " + cust.getState("Name"));
		}

	}
	
	//----------------------------------------------------------------------------------
	public Patron findPatronOlderThan(String date)
	{
		return null;
	}
	
	//------------------------------------------------------------------------------------
	public Patron findPatronYoungerThan(String date)
	{
		return null;
	}
	
	//------------------------------------------------------------------------------------
	public Patron findPatronAtZip(String zip)
	{
		return null;
	}
	
	//------------------------------------------------------------------------------------
	public Patron findPatronWithNameLike(String name)
	{
		return null;
	}

	//----------------------------------------------------------------------------------
	private void addPatron(Patron a)
	{
		//accounts.add(a);
		int index = findIndexToAdd(a);
		accounts.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Patron a)
	{
		//users.add(u);
		int low=0;
		int high = accounts.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Patron midSession = accounts.elementAt(middle);

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
			return accounts;
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
		for (int cnt = 0; cnt < accounts.size(); cnt++)
		{
			Patron nextPatr = accounts.elementAt(cnt);
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
