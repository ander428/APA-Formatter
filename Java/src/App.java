import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App
{
	//frame
	private JPanel mainFrame;
	private JPanel pInput;
	private JPanel grpSpacers;
	private JPanel pItemsA;
	private JPanel grpTitle;
	private JPanel pItemsSI;
	private JPanel pItemsPages;
	private JPanel pItemsLocation;
	private JPanel pItemsDate;

	//Buttons
	private JPanel grpButtons;
	private JButton btnFormat;
	private JButton btnClear;

	//output (text area)
	private JTextPane richTextBox1;
	private JLabel lblCite;

	//author group
	private JPanel grpAuthor;
	private JLabel lblFirst;
	private JLabel lblMI;
	private JLabel lblLast;
	private JLabel lblOR1;
	private JLabel lblArticle;
	private JTextField txtFirst1;
	private JTextField txtMI1;
	private JTextField txtLast1;
	private JTextField txtFirst2;
	private JTextField txtMI2;
	private JTextField txtLast2;
	private JTextField txtArticle;

	//publisher group
	private JPanel grpPublisher;
	private JLabel lblCity;
	private JLabel lblPublisher;
	private JLabel lblState;
	private JLabel lblDate;
	private JLabel lblMonth;
	private JLabel lblDay;
	private JLabel lblYear;
	private JTextField txtCity;
	private JTextField txtState;
	private JTextField txtPublisher;
	private JTextField txtPM;
	private JTextField txtPD;
	private JTextField txtPY;

	//source info group
	private JTextField txtTitle;
	private JLabel lblSubSection;
	private JLabel lblPages;
	private JLabel label3;
	private JLabel lblOR2;
	private JLabel lblPara;
	private JTextField txtSubSection;
	private JTextField txtPg1;
	private JTextField txtPg2;
	private JTextField txtPara;

	//URL group
	private JPanel grpURL;
	private JTextField txtURL;

	//comboBox
	private JComboBox comboBox1;

	//color indicators
	private JLabel lblGreen;
	private JLabel lblYellow;
	private JLabel lblRed;
	private JPanel main;
	private JMenuBar menuBar;

	//textbox vars
	ArrayList<String> states = new ArrayList<String>();
	ArrayList<String> statesABR = new ArrayList<String>();
	int resultBook;
	//String datePattern = @"^(0?[1-9]|1[012])[\/-](0?[1-9]|1[0-9]|2[0-9]|3[01])[\/-]\d{4}$"; //checks for a valid date
	private int selection;
	private String URL;
	private String title;
	private String article;
	private String first1;
	private String first2;
	private String MI1;
	private String MI2;
	private String last1;
	private String last2;
	private String PD;
	private String PM;
	private String PY;
	private String city;
	private String state;
	private String stateAbrs;
	private String publisher;
	private String chapter;
	private String pg1;
	private String pg2;
	private String paragraph;
	private String published = "";
	private String name1 = "";
	private String name2 = "";
	private String errors = "Invalid Inputs:";

	String datePattern = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\\\d\\\\d)"; //checks for a valid date

	public App() {

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});

		comboBox1.addItem("Please Select a Source");
		comboBox1.addItem("Website");
		comboBox1.addItem("Book");
		comboBox1.addItem("Chapter of a Book");
		comboBox1.addItem("Journal");
		comboBox1.addItem("Online Newspaper");


		btnFormat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox1.getSelectedIndex() == 0) JOptionPane.showMessageDialog(null,"Please Select a Category");
				else formatSource();

				if (!errors.equals("Invalid Inputs:")) JOptionPane.showMessageDialog(null, errors);

				errors = "Invalid Inputs:";
			}
		});
		comboBox1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp = comboBox1.getSelectedIndex();
				clearForm();
				comboBox1.setSelectedIndex(temp);
				onComboBoxChanged();
			}
		});
	}

	private void onComboBoxChanged()
	{
		//changes controls appearance for Website
		if (comboBox1.getSelectedIndex() == 1)
		{
			lblDate.setForeground(Color.GREEN);
			lblDay.setForeground(Color.GREEN);
			lblMonth.setForeground(Color.GREEN);
			lblYear.setForeground(Color.GREEN);

			lblFirst.setForeground(Color.ORANGE);
			lblMI.setForeground(Color.ORANGE);
			lblLast.setForeground(Color.GREEN);
			lblArticle.setForeground(Color.ORANGE);

			lblCity.setForeground(Color.RED);
			lblState.setForeground(Color.RED);
			lblPublisher.setForeground(Color.RED);

			lblSubSection.setForeground(Color.RED);
			lblPages.setForeground(Color.RED);
			lblPara.setForeground(Color.RED);

			updateGroup(grpURL, "URL", Color.GREEN);
			updateGroup(grpTitle, "Source Info", Color.GREEN);
			updateGroup(grpAuthor, "Website/Article Contributors:", Color.GREEN);
			updateGroup(grpPublisher, "Publisher: ", Color.GREEN);

			lblDate.setText("Last Edited:");

			selection = 1;
		}
		//changes controls appearance for Book
		else if (comboBox1.getSelectedIndex() == 2)
		{
			resultBook = JOptionPane.showConfirmDialog(null,"Is the Book an online copy?", "Book Citation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resultBook != JOptionPane.CANCEL_OPTION)
			{
				lblDate.setForeground(Color.GREEN);
				lblMonth.setForeground(Color.RED);
				lblDay.setForeground(Color.RED);
				lblYear.setForeground(Color.GREEN);

				lblFirst.setForeground(Color.GREEN);
				lblMI.setForeground(Color.ORANGE);
				lblLast.setForeground(Color.GREEN);
				lblArticle.setForeground(Color.RED);

				lblSubSection.setForeground(Color.RED);
				lblPages.setForeground(Color.RED);
				lblPara.setForeground(Color.RED);

				updateGroup(grpAuthor, "Author:", Color.GREEN);
				updateGroup(grpTitle, "Source Info", Color.GREEN);

				if (resultBook == JOptionPane.YES_OPTION)
				{
					updateGroup(grpURL, "URL", Color.GREEN);
					updateGroup(grpPublisher, "Publisher: ", Color.RED);
					lblPublisher.setForeground(Color.RED);
					lblCity.setForeground(Color.RED);
					lblState.setForeground(Color.RED);
				}

				else if (resultBook == JOptionPane.NO_OPTION)
				{
					updateGroup(grpURL, "URL", Color.RED);
					updateGroup(grpPublisher, "Publisher: ", Color.GREEN);
					lblPublisher.setForeground(Color.GREEN);
					lblCity.setForeground(Color.GREEN);
					lblState.setForeground(Color.GREEN);
				}

				lblDate.setText("Enter Year Published:");

				selection = 2;
			}


		}
		//changes controls appearance for Chapter of a Book
		else if (comboBox1.getSelectedIndex() == 3)
		{
			resultBook = JOptionPane.showConfirmDialog(null,"Is the Book an online copy?", "Chapter Citation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resultBook != JOptionPane.CANCEL_OPTION)
			{

				lblDate.setForeground(Color.GREEN);
				lblMonth.setForeground(Color.RED);
				lblDay.setForeground(Color.RED);
				lblYear.setForeground(Color.GREEN);

				lblFirst.setForeground(Color.GREEN);
				lblMI.setForeground(Color.ORANGE);
				lblLast.setForeground(Color.GREEN);
				lblArticle.setForeground(Color.RED);

				lblSubSection.setForeground(Color.GREEN);
				lblPages.setForeground(Color.ORANGE);
				lblPara.setForeground(Color.ORANGE);

				updateGroup(grpAuthor, "Author:", Color.GREEN);
				updateGroup(grpTitle, "Source Info", Color.GREEN);


				if (resultBook == JOptionPane.YES_OPTION)
				{
					updateGroup(grpURL, "URL", Color.GREEN);
					updateGroup(grpPublisher, "Publisher: ", Color.RED);
					lblPublisher.setForeground(Color.RED);
					lblCity.setForeground(Color.RED);
					lblState.setForeground(Color.RED);
				}

				else if (resultBook == JOptionPane.NO_OPTION)
				{
					updateGroup(grpURL, "URL", Color.RED);
					updateGroup(grpPublisher, "Publisher: ", Color.GREEN);
					lblPublisher.setForeground(Color.GREEN);
					lblCity.setForeground(Color.GREEN);
					lblState.setForeground(Color.GREEN);
				}

				lblDate.setText("Enter Year Published:");

				selection = 3;
			}
		}
		//changes controls appearance for Journal
		else if (comboBox1.getSelectedIndex() == 4)
		{
			resultBook = JOptionPane.showConfirmDialog(null,"Is the Journal an online copy?", "Journal Citation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resultBook != JOptionPane.CANCEL_OPTION)
			{
				clearForm();
				lblDate.setForeground(Color.GREEN);
				lblMonth.setForeground(Color.RED);
				lblDay.setForeground(Color.RED);
				lblYear.setForeground(Color.GREEN);

				lblFirst.setForeground(Color.ORANGE);
				lblMI.setForeground(Color.ORANGE);
				lblLast.setForeground(Color.GREEN);
				lblArticle.setForeground(Color.GREEN);

				lblSubSection.setForeground(Color.RED);
				lblPages.setForeground(Color.GREEN);
				lblPara.setForeground(Color.GREEN);;
				lblPara.setForeground(Color.GREEN);

				updateGroup(grpAuthor, "Author:", Color.GREEN);
				updateGroup(grpTitle, "Source Info", Color.GREEN);

				if (resultBook == JOptionPane.YES_OPTION)
				{
					updateGroup(grpURL, "URL or doi #", Color.GREEN);
					updateGroup(grpAuthor, "Author:", Color.RED);
					lblPublisher.setForeground(Color.RED);
					lblCity.setForeground(Color.RED);
					lblState.setForeground(Color.RED);
				}

				else if (resultBook == JOptionPane.NO_OPTION)
				{
					updateGroup(grpURL, "URL or doi #", Color.ORANGE);
					updateGroup(grpAuthor, "Author:", Color.RED);
					lblPublisher.setForeground(Color.RED);
					lblCity.setForeground(Color.RED);
					lblState.setForeground(Color.RED);
				}

				lblOR1.setText("AND");
				lblOR2.setText("AND");
				lblPara.setText("Vo(Issue)");
				lblDate.setText("Enter Year Published:");

				selection = 4;
			}
		}
		//changes controls appearance for Online Newspaper
		else if (comboBox1.getSelectedIndex() == 5)
		{
			lblDate.setForeground(Color.GREEN);
			lblDay.setForeground(Color.GREEN);
			lblMonth.setForeground(Color.GREEN);
			lblYear.setForeground(Color.GREEN);

			lblFirst.setForeground(Color.ORANGE);
			lblMI.setForeground(Color.ORANGE);
			lblLast.setForeground(Color.GREEN);
			lblArticle.setForeground(Color.GREEN);

			lblCity.setForeground(Color.RED);
			lblState.setForeground(Color.RED);
			lblPublisher.setForeground(Color.RED);

			lblSubSection.setForeground(Color.RED);
			lblPages.setForeground(Color.RED);
			lblPara.setForeground(Color.RED);

			updateGroup(grpURL, "URL", Color.GREEN);
			updateGroup(grpTitle, "Source Info", Color.GREEN);
			updateGroup(grpAuthor, "News Article Contributors:", Color.GREEN);
			updateGroup(grpPublisher, "Publisher: ", Color.RED);

			lblDate.setText("Last Edited:");
			lblOR1.setText("AND");

			selection = 5;
		}
	}

	private void AddToList(ArrayList<String> list, String... paramList)
	{
		for(String arg : paramList)
		{
			list.add(arg);
		}
	}

	private void clearForm()
	{
		lblGreen.setForeground(Color.GREEN);
		lblYellow.setForeground(Color.ORANGE);
		lblRed.setForeground(Color.RED);

		resetContainer(grpAuthor);
		resetContainer(grpPublisher);
		resetContainer(grpTitle);
		resetContainer(grpURL);

		updateGroup(grpURL, "URL", Color.BLACK);
		updateGroup(grpTitle, "Source Info", Color.BLACK);
		updateGroup(grpAuthor, "Website/Article Contributors:", Color.BLACK);
		updateGroup(grpPublisher, "Publisher: ", Color.BLACK);

		comboBox1.setSelectedIndex(0);
		setRichTextBox1(richTextBox1, " ");
		richTextBox1.setEditable(true);

		lblOR1.setText("OR");
		lblOR2.setText("OR");
	}

	private void resetContainer(Container c)
	{
		Component[] components = c.getComponents();

		for(Component e : components)
		{
			if(e instanceof JLabel) e.setForeground(Color.BLACK);
			if(e instanceof JTextField) ((JTextField) e).setText("");
			else if(e instanceof Container) resetContainer((Container) e);
		}
	}

	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
				{
					ex.printStackTrace();
				}

				JFrame frame = new JFrame("APA Formatter");
				App app = new App();
				frame.setContentPane(app.mainFrame);



				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setSize(1500,800);
				frame.setVisible(true);
				frame.repaint();

				app.start(frame);

			}
		});
	}

	private void start(JFrame frame)
	{
		setMenuBar(frame);
		clearForm();
		setStates();
	}

	private void setMenuBar(JFrame frame)
	{
		this.menuBar = new JMenuBar();
		frame.setJMenuBar(this.menuBar);

		//creates items
		JMenu file = new JMenu("File");
		JMenuItem website = new JMenuItem("Website Key");
		JMenuItem book = new JMenuItem("Book Key");
		JMenuItem bookChapter = new JMenuItem("Book Chapter Key");
		JMenuItem journal = new JMenuItem("Journal");
		JMenuItem onlineNews = new JMenuItem("Online Newspaper Key");

		//adds items to menu
		file.add(website);
		file.add(book);
		file.add(bookChapter);
		file.add(journal);
		file.add(onlineNews);
		menuBar.add(file);

		//adds action listeners to menu items
		createMessageActionListener(website, "Last, F. M. (Year, Month Date Published). Website title. Retrieved from URL\n\nExample:\n" +
				"Satalkar, B. (2010, July 15). Water aerobics. Retrieved from http://www.buzzle.com");
		createMessageActionListener(book, "Last, F. M. (Year Published) Book Title. City, State: Publisher.\n\nExample:\n" +
				"James, H. (1937). The ambassadors. New York, NY: Scribner.");
		createMessageActionListener(bookChapter, "Last, F. M. (Year Published). Title of chapter In F. M. Last Editor (Ed.), Title of book/anthology (pp. Pages). " +
				"Publisher City, State: Publisher.\n\nExample:\nServiss, G. P. (1911). A trip of terror. In A Columbus of space (pp. 17-32). " +
				"New York, NY: Appleton.");
		createMessageActionListener(journal, "Last, F. M., & Last, F. M. (Year Published). Article title. Journal Name, Volume(Issue), pp. Pages.\n\nExample:\n" +
				"Jacoby, W. G. (1994). Public attitudes toward government spending. American Journal of Political Science, 38(2), 336-361.");
		createMessageActionListener(onlineNews, "Last, F. M. (Year, Month Date Published). Article title. Newspaper Title, Retrieved from URL.\n\nExample:\n" +
				"Bowman, L. (1990, March 7). Bills target Lake Erie mussels. The Pittsburgh Press, Retrieved from http://www.pittsburghpress.com");

	}

	private void createMessageActionListener(JMenuItem item, String message)
	{
		class action implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, message);
			}
		}

		item.addActionListener(new action());
	}

	private void setStates()
	{
		AddToList(states ,"Alaska",
				"Alabama",
				"Arkansas",
				"American Samoa",
				"Arizona",
				"California",
				"Colorado",
				"Connecticut",
				"District of Columbia",
				"Delaware",
				"Florida",
				"Georgia",
				"Guam",
				"Hawaii",
				"Iowa",
				"Idaho",
				"Illinois",
				"Indiana",
				"Kansas",
				"Kentucky",
				"Louisiana",
				"Massachusetts",
				"Maryland",
				"Maine",
				"Michigan",
				"Minnesota",
				"Missouri",
				"Mississippi",
				"Montana",
				"North Carolina",
				"North Dakota",
				"Nebraska",
				"New Hampshire",
				"New Jersey",
				"New Mexico",
				"Nevada",
				"New York",
				"Ohio",
				"Oklahoma",
				"Oregon",
				"Pennsylvania",
				"Puerto Rico",
				"Rhode Island",
				"South Carolina",
				"South Dakota",
				"Tennessee",
				"Texas",
				"Utah",
				"Virginia",
				"Virgin Islands",
				"Vermont",
				"Washington",
				"Wisconsin",
				"West Virginia",
				"Wyoming",
				"United Kingdom");

		AddToList(statesABR, "AK",
				"AL",
				"AR",
				"AS",
				"AZ",
				"CA",
				"CO",
				"CT",
				"DC",
				"DE",
				"FL",
				"GA",
				"GU",
				"HI",
				"IA",
				"ID",
				"IL",
				"IN",
				"KS",
				"KY",
				"LA",
				"MA",
				"MD",
				"ME",
				"MI",
				"MN",
				"MO",
				"MS",
				"MT",
				"NC",
				"ND",
				"NE",
				"NH",
				"NJ",
				"NM",
				"NV",
				"NY",
				"OH",
				"OK",
				"OR",
				"PA",
				"PR",
				"RI",
				"SC",
				"SD",
				"TN",
				"TX",
				"UT",
				"VA",
				"VI",
				"VT",
				"WA",
				"WI",
				"WV",
				"WY",
				"UK");
	}

	//swtiches a numerical value to String for months
	public String switchMonth(String month)
	{
		int x = 0;
		boolean successful = false;
		try
		{
			x = Integer.parseInt(month);
			successful = true;
		}
		catch (NumberFormatException e) { }

		if (successful)
		{
			if (x > 0 && x <= 12)
			{
				switch (x)
				{
					case 1:
						month = "January";
						break;
					case 2:
						month = "February";
						break;
					case 3:
						month = "March";
						break;
					case 4:
						month = "April";
						break;
					case 5:
						month = "May";
						break;
					case 6:
						month = "June";
						break;
					case 7:
						month = "July";
						break;
					case 8:
						month = "August";
						break;
					case 9:
						month = "September";
						break;
					case 10:
						month = "October";
						break;
					case 11:
						month = "November";
						break;
					case 12:
						month = "December";
						break;

				}
			}
		}
		return month;
	}


	//makes the first letter of a String upper
	private String UppercaseFirst(String startingString)
	{
		String s = startingString.substring(0,1).toUpperCase();
		s += startingString.substring(1);

		return s;
	}


	//forms the publlished String or returns an error
	private void formPublished(String day, String month, String year)
	{
		month = switchMonth(month);
		if (selection == 1 || selection == 5)
		{
			if (day.length() > 0 && month.length() > 0 && year.length() > 0)
			{
				published = "(" + year + ", " + UppercaseFirst(month) + " " + day + "). ";
			}
			else if (day.length() == 0 && month.length() > 0 && year.length() > 0)
			{
				published = "(" + year + ", " + UppercaseFirst(month) + "). ";
			}
			else if (day.length() == 0 && month.length() == 0 && year.length() == 0)
			{
				published = "(n.d.). ";
			}
			else errors += "\nDate";
		}

		else
		{
			if (day.length() == 0 && month.length() == 0 && year.length() > 0)
			{
				published = "(" + year + "). ";
			}

			else if (day.length() == 0 && month.length() == 0 && year.length() == 0)
			{
				published = "(n.d.). ";
			}
			//else errors += "\nDate";
		}
	}

	//forms the name String or returns an error
	private String formName(String first, String middle, String last, int a)
	{
		String person = "";
		if (first.length() > 0 && middle.length() > 0 && last.length() > 0)
		{
			person = UppercaseFirst(last) + ", " + UppercaseFirst(first.substring(0, 1)) +
					". " + UppercaseFirst(middle.substring(0, 1)) + ". ";
		}
		else if (middle.length() == 0 && first.length() > 0 && last.length() > 0)
		{
			person = UppercaseFirst(last) + ", " + UppercaseFirst(first.substring(0, 1)) + ". ";
		}
		else if (middle.length() == 0 && first.length() == 0 && last.length() > 0)
		{
			person = UppercaseFirst(last) + ". ";
		}
		return person;
	}


	private boolean IsUrlValid(String url)
	{
		String pattern = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>";
		return url.matches(pattern);
	}

	//forms the state abr
	private String formState()
	{
		String formState = "";

		if (states.contains(UppercaseFirst(state)))
		{
			int index = states.indexOf(state);
			formState = statesABR.get(index);
		}
		else formState = state;

		return formState;
	}

	//puts together the citation String
	private String citation()
	{
		String citation = "";
		boolean name1bool = false;
		if (name1.equals(null) == false)
		{
			citation += name1;
			name1bool = true;
		}
		if (name2.equals(null) == false)
		{
			if (name1bool) citation += "& " + name2;
			else citation += name2;
		}
		if (name1.equals(null) && name2.equals(null))
		{
			if (selection != 4 && !article.equals(null)) citation += article + ". ";
			else
			{
				citation += "Anonymous. ";
				errors += "\nAuthor/Contributor";
			}
		}
		if (published.equals(null) == false)
		{
			citation += published;
		}

		if (selection == 3)
		{
			if (chapter.equals(null) == false) citation += chapter + ". In ";
			else errors += "\nChapter Title";
		}

		if (selection == 4 || selection == 5)
		{
			if (article.equals(null) == false) citation += article + ". ";
			else errors += "\nArticle Title";
		}

		if (title.equals(null) == false)
		{
			citation += title;
			if (selection == 3) citation += ", ";
			else citation += " ";
		}

		return citation;
	}

	//used in the case of a web source
	private String webCitation()
	{
		String citation = "";

		if (URL.equals(null) == false)
		{
			if (IsUrlValid(URL))
			{
				citation += "Retrieved from " + URL;
			}

			else
			{
				if (selection == 4) citation += "doi: " + URL;
				else errors += "\nURL or doi #";
			}
		}
		else errors += "\nURL or doi #";
		return citation;
	}

	//used for sources related to a book
	private String bookCitation()
	{
		String citation = "";

		if (selection == 3)
		{
			if (pg1.equals(null) == false)
			{
				if (pg2.equals(null)) citation += "(pp. " + pg1 + "). ";
				else citation += "(pp. " + pg1 + "-" + pg2 + "). ";
			}
			else if (pg1.equals(null) && pg2.equals(null) && paragraph.equals(null) == false)
			{
				citation += "(para. " + paragraph + "). ";
			}
			else errors += "\nPage/Paragraph Number";
		}
		else if(selection == 4)
		{
			//using he paragraph textbox as the volume(issue)
			if (paragraph.equals(null) == false) citation += paragraph + ". ";

			if (pg1.equals(null) == false)
			{
				if (pg2.equals(null) == false) citation += pg1 + "-" + pg2 + ". ";
				else citation += pg1 + ". ";
			}
			else errors += "\nPage/Volume";

		}

		if (city.equals(null) == false) citation += UppercaseFirst(city) + ", ";
		if (state.equals(null) == false) citation += stateAbrs;
		if (publisher.equals(null) == false) citation += ": " + UppercaseFirst(publisher) + ". ";
		return citation;
	}

	//the formatting method
	private void formatSource()
	{
		title = txtTitle.getText();
		PD = txtPD.getText();
		PM = txtPM.getText();
		PY = txtPY.getText();
		article = txtArticle.getText();
		first1 = txtFirst1.getText();
		first2 = txtFirst2.getText();
		MI1 = txtMI1.getText();
		MI2 = txtMI2.getText();
		last1 = txtLast1.getText();
		last2 = txtLast2.getText();

		//formats Strings to be used by citation methods
		formPublished(PD, PM, PY);
		name1 = formName(first1, MI1, last1, 1);
		name2 = formName(first2, MI2, last2, 2);

		//Website
		if (selection == 1 || selection == 5)
		{
			URL = txtURL.getText();
			String date = txtPM.getText() + "/" + txtPD.getText() + "/" + txtPY.getText();
			if (!date.matches(datePattern)) errors += "\nDate";
			setRichTextBox1(richTextBox1, citation() + webCitation());
			richTextBox1.setEditable(true);
		}

		//Book
		else if (selection == 2)
		{
			city = txtCity.getText();
			publisher = txtPublisher.getText();
			state = txtState.getText();

			if (resultBook == JOptionPane.YES_OPTION)
			{
				URL = txtURL.getText();
				setRichTextBox1(richTextBox1, citation() + webCitation());
				richTextBox1.setEditable(true);
			}

			else if(resultBook == JOptionPane.NO_OPTION)
			{
				stateAbrs = formState();
				setRichTextBox1(richTextBox1, citation() + bookCitation());
				richTextBox1.setEditable(true);
			}
		}

		//Journal or Chapter of a Book respectively
		if(selection == 4 || selection == 3)
		{
			city = txtCity.getText();
			publisher = txtPublisher.getText();
			state = txtState.getText();
			stateAbrs = formState();
			paragraph = txtPara.getText();
			pg1 = txtPg1.getText();
			pg2 = txtPg2.getText();
			chapter = txtSubSection.getText();

			if (resultBook == JOptionPane.YES_OPTION)
			{
				URL = txtURL.getText();
				setRichTextBox1(richTextBox1, citation() + bookCitation() + webCitation());
				richTextBox1.setEditable(true);
		}

			else if(resultBook == JOptionPane.NO_OPTION)
			{
				stateAbrs = formState();

				setRichTextBox1(richTextBox1, citation() + bookCitation());
				richTextBox1.setEditable(true);
			}
		}

		//sets text font
		richTextBox1.setFont(new Font("Times New Roman", 12, Font.PLAIN));

		//italicizes title
		Font fnt = new Font("Times New Roman", 12,Font.ITALIC);
		if (richTextBox1.getText().contains(title))
		{
			int my1stPosition = richTextBox1.getText().indexOf(title);
			StyledDocument doc = richTextBox1.getStyledDocument();

			Style style = richTextBox1.addStyle("Red", null);
			StyleConstants.setForeground(style, Color.red);

			// Inherits from "Red"; makes text red and underlined
			style = richTextBox1.addStyle("Red Underline", style);
			StyleConstants.setUnderline(style, true);
		}
	}

	private void setRichTextBox1(JTextPane textPane, String input)
	{
		StyledDocument doc = textPane.getStyledDocument();

//  Define a keyword attribute

		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.RED);
		StyleConstants.setBackground(keyWord, Color.YELLOW);
		StyleConstants.setBold(keyWord, true);

//  Add some text

		try
		{
			doc.insertString(0, input, null );
		}
		catch(Exception e) { System.out.println(e); }
	}

	private void updateGroup(JPanel grp, String title, Color color)
	{
		Border temp = BorderFactory.createEtchedBorder();
		TitledBorder border = new TitledBorder(temp,title,TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,null,color);
		grp.setBorder(border);
	}


}


