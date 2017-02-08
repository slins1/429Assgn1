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


/** The class containing the BookCollection for the Library application */
//==============================================================
public class BookCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Book";

	private Vector<Book> books;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public BookCollection(String tablename) {
		super(tablename);
		books = new Vector<Book>();
	}

	//----------------------------------------------------------------------------------
	public Book findBooksOlderThanDate(String year)
	{
		return null;
	}
	
	//------------------------------------------------------------------------------------
	public Book findBooksNewerThanDate(String year)
	{

		return null;
	}


	//------------------------------------------------------------------------------------
	public Book findBooksWithTitleLike(String title)
	{
		String sqlSelectStatement = "SELECT * FROM " + myTableName + " WHERE title LIKE %"
				+ title + "%";
		return null;
	}

	//------------------------------------------------------------------------------------
	public Book findBooksWithAuthorLike(String author)
	{
		return null;
	}

	//----------------------------------------------------------------------------------
	private void addBook(Book a)
	{
		//books.add(a);
		int index = findIndexToAdd(a);
		books.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Book a)
	{
		//users.add(u);
		int low=0;
		int high = books.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Book midSession = books.elementAt(middle);

			int result = Book.compare(a,midSession);

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


	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("stuff"))
			return books;
		else
		return null;
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

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
}
