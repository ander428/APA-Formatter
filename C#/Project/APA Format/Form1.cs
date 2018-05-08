using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Text.RegularExpressions;


namespace APA_Format
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            
        }
        
        List<string> states = new List<string>();
        List<string> statesABR = new List<string>();
        DialogResult resultBook;
        string datePattern = @"^(0?[1-9]|1[012])[\/-](0?[1-9]|1[0-9]|2[0-9]|3[01])[\/-]\d{4}$"; //checks for a valid date
        private int selection;
        private string URL;
        private string title;
        private string article;
        private string first1;
        private string first2;
        private string MI1;
        private string MI2;
        private string last1;
        private string last2;
        private string PD;
        private string PM;
        private string PY;
        private string city;
        private string state;
        private string stateAbrs;
        private string publisher;
        private string chapter;
        private string pg1;
        private string pg2;
        private string paragraph;
        private string published = "";
        private string name1 = "";
        private string name2 = "";
        private string errors = "Invalid Inputs:";

        [System.Runtime.InteropServices.DllImport("user32.dll")]
        private static extern bool SetProcessDPIAware();

        private void Form1_Load(object sender, EventArgs e)
        {
            //Height = (int)((float)Height * ((float)Screen.PrimaryScreen.Bounds.Size.Height));
            Width = (int)((float)Width * ((float)Screen.PrimaryScreen.Bounds.Size.Width / (float)1225));

            ClearForm();

            lblGreen.ForeColor = System.Drawing.Color.LimeGreen;
            lblYellow.ForeColor = System.Drawing.Color.Orange;
            lblRed.ForeColor = System.Drawing.Color.OrangeRed;

            comboBox1.Items.Add("Website");
            comboBox1.Items.Add("Book");
            comboBox1.Items.Add("Chapter of a Book");
            comboBox1.Items.Add("Journal");
            comboBox1.Items.Add("Online Newspaper");

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

        private void AddToList(List<string> list, params string[] paramList)
        {
            for (int i = 0; i < paramList.Length; i++)
            {
                list.Add(paramList[i]);
            }
        }

        //swtiches a numerical value to string for months
        public string switchMonth(string month)
        {
            int x = 0;
            if (Int32.TryParse(month, out x))
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

        //makes the first letter of a string upper
        private string UppercaseFirst(string startingString)
        {
            startingString = startingString.ToLower();
            char[] chars = new[] { '-',' '};
            StringBuilder result = new StringBuilder(startingString.Length);
            bool makeUpper = true;
            foreach (var c in startingString)
            {
                if (makeUpper)
                {
                    result.Append(Char.ToUpper(c));
                    makeUpper = false;
                }
                else
                {
                    result.Append(c);
                }
                if (chars.Contains(c))
                {
                    makeUpper = true;
                }
            }
            return result.ToString();
        }

        //forms the publlished string or returns an error
        private void formPublished(string day, string month, string year)
        {
            month = switchMonth(month);
            if (selection == 1 || selection == 5)
            {
                if (day.Length > 0 && month.Length > 0 && year.Length > 0)
                {
                    published = "(" + year + ", " + UppercaseFirst(month) + " " + day + "). ";
                }
                else if (day.Length == 0 && month.Length > 0 && year.Length > 0)
                {
                    published = "(" + year + ", " + UppercaseFirst(month) + "). ";
                }
                else if (day.Length == 0 && month.Length == 0 && year.Length == 0)
                {
                    published = "(n.d.). ";
                }
                else errors += "\nDate";
            }

            else
            {
                if (day.Length == 0 && month.Length == 0 && year.Length > 0)
                {
                    published = "(" + year + "). ";
                }

                else if (day.Length == 0 && month.Length == 0 && year.Length == 0)
                {
                    published = "(n.d.). ";
                }
                else errors += "\nDate";
            }
        }

        //forms the name string or returns an error
        private string formName(string first, string middle, string last, int a)
        {
            string person = "";
            if (first.Length > 0 && middle.Length > 0 && last.Length > 0)
            {
                person = UppercaseFirst(last) + ", " + UppercaseFirst(first.Substring(0, 1)) +
                    ". " + UppercaseFirst(middle.Substring(0, 1)) + ". ";
            }
            else if (middle.Length == 0 && first.Length > 0 && last.Length > 0)
            {
                person = UppercaseFirst(last) + ", " + UppercaseFirst(first.Substring(0, 1)) + ". ";
            }
            else if (middle.Length == 0 && first.Length == 0 && last.Length > 0)
            {
                person = UppercaseFirst(last) + ". ";
            }
            return person;
        }

        private bool IsUrlValid(string url)
        {

            string pattern = @"^(http|https|ftp|)\://|[a-zA-Z0-9\-\.]+\.[a-zA-Z](:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\-\._\?\,\'/\\\+&amp;%\$#\=~])*[^\.\,\)\(\s]$";
            Regex reg = new Regex(pattern, RegexOptions.Compiled | RegexOptions.IgnoreCase);
            return reg.IsMatch(url);
        }

        //forms the state abr
        private string formState()
        {
            string formState = "";

            if (states.Contains(UppercaseFirst(state)))
            {
                int index = states.FindIndex(x => x.StartsWith(UppercaseFirst(state)));
                formState = statesABR[index];
            }
            else formState = state;

            return formState; 
        }

        //puts together the citation string
        private string citation()
        {
            string citation = "";
            bool name1bool = false;
            if (string.IsNullOrEmpty(name1) == false)
            {
                citation += name1;
                name1bool = true;
            }
            if (string.IsNullOrEmpty(name2) == false)
            {
                if (name1bool) citation += "& " + name2;
                else citation += name2;
            }
            if (string.IsNullOrEmpty(name1) && string.IsNullOrEmpty(name2))
            {
                if (selection != 4 && !string.IsNullOrEmpty(article)) citation += article + ". ";
                else
                {
                    citation += "Anonymous. ";
                    errors += "\nAuthor/Contributor";
                }
            }
            if (string.IsNullOrEmpty(published) == false)
            {
                citation += published;
            }

            if (selection == 3)
            {
                if (string.IsNullOrEmpty(chapter) == false) citation += chapter + ". In ";
                else errors += "\nChapter Title";
            }

            if (selection == 4 || selection == 5)
            {
                if (string.IsNullOrEmpty(article) == false) citation += article + ". ";
                else errors += "\nArticle Title";
            }

            if (string.IsNullOrEmpty(title) == false)
            {
                citation += title;
                if (selection == 3) citation += ", ";
                else citation += " ";
            }

            return citation;
        }

        //used in the case of a web source
        private string webCitation()
        {
            string citation = "";

            if (string.IsNullOrEmpty(URL) == false)
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
        private string bookCitation()
        {
            string citation = "";

            if (selection == 3)
            {
                if (string.IsNullOrEmpty(pg1) == false)
                {
                    if (string.IsNullOrEmpty(pg2)) citation += "(pp. " + pg1 + "). ";
                    else citation += "(pp. " + pg1 + "-" + pg2 + "). ";
                }
                else if (string.IsNullOrEmpty(pg1) && string.IsNullOrEmpty(pg2) && string.IsNullOrEmpty(paragraph) == false)
                {
                    citation += "(para. " + paragraph + "). ";
                }
                else errors += "\nPage/Paragraph Number";
            }
            else if(selection == 4)
            {
                //using he paragraph textbox as the volume(issue)
                if (string.IsNullOrEmpty(paragraph) == false) citation += paragraph + ". ";

                if (string.IsNullOrEmpty(pg1) == false)
                {
                    if (string.IsNullOrEmpty(pg2) == false) citation += pg1 + "-" + pg2 + ". ";
                    else citation += pg1 + ". ";
                }
                else errors += "\nPage/Volume";

            }

            if (string.IsNullOrEmpty(city) == false) citation += UppercaseFirst(city) + ", ";
            if (string.IsNullOrEmpty(state) == false) citation += stateAbrs;
            if (string.IsNullOrEmpty(publisher) == false) citation += ": " + UppercaseFirst(publisher) + ". ";
            return citation;
        }

        //the formatting method
        private void formatSource()
        {
            title = txtTitle.Text;
            PD = txtPD.Text;
            PM = txtPM.Text;
            PY = txtPY.Text;
            article = txtArticle.Text;
            first1 = txtFirst1.Text;
            first2 = txtFirst2.Text;
            MI1 = txtMI1.Text;
            MI2 = txtMI2.Text;
            last1 = txtLast1.Text;
            last2 = txtLast2.Text;

            //formats strings to be used by citation methods
            formPublished(PD, PM, PY);
            name1 = formName(first1, MI1, last1, 1);
            name2 = formName(first2, MI2, last2, 2);

            //Website
            if (selection == 1 || selection == 5)
            {
                URL = txtURL.Text;
                string date = txtPM.Text + "/" + txtPD.Text + "/" + txtPY.Text;
                if (!Regex.IsMatch(date, datePattern)) errors += "\nDate";
                richTextBox1.Text = citation() + webCitation();
            }

            //Book
            else if (selection == 2)
            {
                city = txtCity.Text;
                publisher = txtPublisher.Text;
                state = txtState.Text;

                if (resultBook == DialogResult.Yes)
                {
                    URL = txtURL.Text;
                    richTextBox1.Text = citation() + webCitation();
                }

                else if(resultBook == DialogResult.No)
                {
                    stateAbrs = formState();
                    richTextBox1.Text = citation() + bookCitation();
                }
            }

            //Journal or Chapter of a Book respectively
            if(selection == 4 || selection == 3)
            {
                city = txtCity.Text;
                publisher = txtPublisher.Text;
                state = txtState.Text;
                stateAbrs = formState();
                paragraph = txtPara.Text;
                pg1 = txtPg1.Text;
                pg2 = txtPg2.Text;
                chapter = txtSubSection.Text;

                if (resultBook == DialogResult.Yes)
                {
                    URL = txtURL.Text;
                    richTextBox1.Text = citation() + bookCitation() + webCitation();
                }

                else if(resultBook == DialogResult.No)
                {
                    stateAbrs = formState();

                    richTextBox1.Text = citation() + bookCitation();
                }
            }

            //sets text font
            richTextBox1.Font = new Font("Times New Roman", 12.0f, FontStyle.Regular);

            //italicizes title
            Font fnt = new Font("Times New Roman", 12.0f,FontStyle.Italic);
            if (richTextBox1.Find(title) > 0)
            {
                int my1stPosition = richTextBox1.Find(title);
                richTextBox1.SelectionStart = my1stPosition;
                richTextBox1.SelectionLength = title.Length;
                richTextBox1.SelectionFont = fnt;
            }
        }

        private void btnSelect_Click(object sender, EventArgs e)
        {
            if (comboBox1.Text.Length == 0) MessageBox.Show("Please Select a Category");
            else if (comboBox1.Items.Contains(comboBox1.Text)) formatSource();
            else MessageBox.Show("Not a valid category!");

            if (!errors.Equals("Invalid Inputs:")) MessageBox.Show(errors,"", MessageBoxButtons.OK, MessageBoxIcon.Error);

            errors = "Invalid Inputs:";
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            ClearForm();

            //changes controls appearance for Website
            if (comboBox1.Text.Equals("Website"))
            {
                lblDate.ForeColor = Color.LimeGreen;
                lblDay.ForeColor = Color.LimeGreen;
                lblMonth.ForeColor = Color.LimeGreen;
                lblYear.ForeColor = Color.LimeGreen;

                lblFirst.ForeColor = Color.Orange;
                lblMI.ForeColor = Color.Orange;
                lblLast.ForeColor = Color.LimeGreen;
                lblArticle.ForeColor = Color.Orange;

                lblCity.ForeColor = Color.OrangeRed;
                lblState.ForeColor = Color.OrangeRed;
                lblPublisher.ForeColor = Color.OrangeRed;

                lblSubSection.ForeColor = Color.OrangeRed;
                lblPages.ForeColor = Color.OrangeRed;
                lblPara.ForeColor = Color.OrangeRed;

                grpURL.ForeColor = Color.LimeGreen;
                grpTitle.ForeColor = Color.LimeGreen;
                grpAuthor.ForeColor = Color.LimeGreen;
                grpPublish.ForeColor = Color.OrangeRed;

                lblDate.Text = "Last Edited:";
                grpAuthor.Text = "Website/Article Contributors:";

                selection = 1;
            }
            //changes controls appearance for Book
            else if (comboBox1.Text.Equals("Book"))
            {
                resultBook = MessageBox.Show("Is the Book an online copy?", "Book Citation",
                     MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);

                if (resultBook != DialogResult.Cancel)
                {
                    lblDate.ForeColor = Color.LimeGreen;
                    lblMonth.ForeColor = Color.OrangeRed;
                    lblDay.ForeColor = Color.OrangeRed;
                    lblYear.ForeColor = Color.LimeGreen;

                    lblFirst.ForeColor = Color.LimeGreen;
                    lblMI.ForeColor = Color.Orange;
                    lblLast.ForeColor = Color.LimeGreen;
                    lblArticle.ForeColor = Color.OrangeRed;

                    lblSubSection.ForeColor = Color.OrangeRed;
                    lblPages.ForeColor = Color.OrangeRed;
                    lblPara.ForeColor = Color.OrangeRed;

                    grpAuthor.ForeColor = Color.LimeGreen;
                    grpTitle.ForeColor = Color.LimeGreen;

                    if (resultBook == DialogResult.Yes)
                    {
                        grpURL.ForeColor = Color.LimeGreen;
                        grpPublish.ForeColor = Color.OrangeRed;
                        lblPublisher.ForeColor = Color.OrangeRed;
                        lblCity.ForeColor = Color.OrangeRed;
                        lblState.ForeColor = Color.OrangeRed;
                    }

                    else if (resultBook == DialogResult.No)
                    {
                        grpURL.ForeColor = Color.OrangeRed;
                        grpPublish.ForeColor = Color.LimeGreen;
                        lblPublisher.ForeColor = Color.LimeGreen;
                        lblCity.ForeColor = Color.LimeGreen;
                        lblState.ForeColor = Color.LimeGreen;
                    }

                    lblDate.Text = "Enter Year Published:";
                    grpAuthor.Text = "Author:";

                    selection = 2;
                }


            }
            //changes controls appearance for Chapter of a Book
            else if (comboBox1.Text.Equals("Chapter of a Book"))
            {
                resultBook = MessageBox.Show("Is the Book an online copy?", "Chapter Citation",
                    MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);

                if (resultBook != DialogResult.Cancel)
                {

                    lblDate.ForeColor = Color.LimeGreen;
                    lblMonth.ForeColor = Color.OrangeRed;
                    lblDay.ForeColor = Color.OrangeRed;
                    lblYear.ForeColor = Color.LimeGreen;

                    lblFirst.ForeColor = Color.LimeGreen;
                    lblMI.ForeColor = Color.Orange;
                    lblLast.ForeColor = Color.LimeGreen;
                    lblArticle.ForeColor = Color.OrangeRed;

                    grpAuthor.ForeColor = Color.LimeGreen;
                    grpTitle.ForeColor = Color.LimeGreen;

                    lblSubSection.ForeColor = Color.LimeGreen;
                    lblPages.ForeColor = Color.Orange;
                    lblPara.ForeColor = Color.Orange;

                    if (resultBook == DialogResult.Yes)
                    {
                        grpURL.ForeColor = Color.LimeGreen;
                        grpPublish.ForeColor = Color.OrangeRed;
                        lblPublisher.ForeColor = Color.OrangeRed;
                        lblCity.ForeColor = Color.OrangeRed;
                        lblState.ForeColor = Color.OrangeRed;
                    }

                    else if (resultBook == DialogResult.No)
                    {
                        grpURL.ForeColor = Color.OrangeRed;
                        grpPublish.ForeColor = Color.LimeGreen;
                        lblPublisher.ForeColor = Color.LimeGreen;
                        lblCity.ForeColor = Color.LimeGreen;
                        lblState.ForeColor = Color.LimeGreen;
                    }

                    lblDate.Text = "Enter Year Published:";
                    grpAuthor.Text = "Author:";

                    selection = 3;
                }
            }
            //changes controls appearance for Journal
            else if (comboBox1.Text.Equals("Journal"))
            {
                resultBook = MessageBox.Show("Is the Journal an online copy?", "Journal Citation",
    MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);

                if (resultBook != DialogResult.Cancel)
                {
                    ClearForm();
                    lblDate.ForeColor = Color.LimeGreen;
                    lblMonth.ForeColor = Color.OrangeRed;
                    lblDay.ForeColor = Color.OrangeRed;
                    lblYear.ForeColor = Color.LimeGreen;

                    lblFirst.ForeColor = Color.Orange;
                    lblMI.ForeColor = Color.Orange;
                    lblLast.ForeColor = Color.LimeGreen;
                    lblArticle.ForeColor = Color.LimeGreen;
                    
                    grpAuthor.ForeColor = Color.LimeGreen;
                    grpTitle.ForeColor = Color.LimeGreen;

                    lblSubSection.ForeColor = Color.OrangeRed;
                    lblPages.ForeColor = Color.LimeGreen;
                    lblPara.ForeColor = Color.LimeGreen;;
                    lblPara.ForeColor = Color.LimeGreen;

                    if (resultBook == DialogResult.Yes)
                    {
                        grpURL.Text = "URL or doi #";
                        grpURL.ForeColor = Color.LimeGreen;
                        grpPublish.ForeColor = Color.OrangeRed;
                        lblPublisher.ForeColor = Color.OrangeRed;
                        lblCity.ForeColor = Color.OrangeRed;
                        lblState.ForeColor = Color.OrangeRed;
                    }

                    else if (resultBook == DialogResult.No)
                    {
                        grpURL.Text = "doi #";
                        grpURL.ForeColor = Color.Orange;
                        grpPublish.ForeColor = Color.OrangeRed;
                        lblPublisher.ForeColor = Color.OrangeRed;
                        lblCity.ForeColor = Color.OrangeRed;
                        lblState.ForeColor = Color.OrangeRed;
                    }

                    lblOR1.Text = "AND";
                    lblOR2.Text = "AND";
                    lblPara.Text = "Vo(Issue)";
                    lblDate.Text = "Enter Year Published:";
                    grpAuthor.Text = "Author:";

                    selection = 4;
                }
            }
            //changes controls appearance for Online Newspaper
            else if (comboBox1.Text.Equals("Online Newspaper"))
            {
                lblDate.ForeColor = Color.LimeGreen;
                lblDay.ForeColor = Color.LimeGreen;
                lblMonth.ForeColor = Color.LimeGreen;
                lblYear.ForeColor = Color.LimeGreen;

                lblFirst.ForeColor = Color.Orange;
                lblMI.ForeColor = Color.Orange;
                lblLast.ForeColor = Color.LimeGreen;
                lblArticle.ForeColor = Color.LimeGreen;

                lblCity.ForeColor = Color.OrangeRed;
                lblState.ForeColor = Color.OrangeRed;
                lblPublisher.ForeColor = Color.OrangeRed;

                lblSubSection.ForeColor = Color.OrangeRed;
                lblPages.ForeColor = Color.OrangeRed;
                lblPara.ForeColor = Color.OrangeRed;

                grpURL.ForeColor = Color.LimeGreen;
                grpTitle.ForeColor = Color.LimeGreen;
                grpAuthor.ForeColor = Color.LimeGreen;
                grpPublish.ForeColor = Color.OrangeRed;

                lblDate.Text = "Last Edited:";
                grpAuthor.Text = "News Article Contributors:";
                lblOR1.Text = "AND";

                selection = 5;
            }
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            ClearForm();

            lblGreen.ForeColor = Color.LimeGreen;
            lblYellow.ForeColor = Color.Orange;
            lblRed.ForeColor = Color.OrangeRed;

            comboBox1.Text = "";
        }

        //resets all the controls to their starting state
        private void ClearForm()
        {
            Action<Control.ControlCollection> func = null;

            func = (controls) =>
            {
                foreach (Control control in controls)
                {
                    if (control is Label) (control as Label).ForeColor = Color.White;
                    else func(control.Controls);

                    if (control is TextBox) (control as TextBox).Clear();
                    else func(control.Controls);

                    if (control is GroupBox) (control as GroupBox).ForeColor = Color.White;
                    else func(control.Controls);
                }
            };

            func(Controls);

            lblOR1.Text = "OR";
            lblOR2.Text = "OR";
            lblPara.Text = "¶:";
            lblDate.Text = "Date:";
            grpURL.Text = "URL:";

           lblGreen.ForeColor = Color.LimeGreen;
           lblYellow.ForeColor = Color.Orange;
           lblRed.ForeColor = Color.OrangeRed;

            richTextBox1.Clear();
        }

        private void websiteKeyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Last, F. M. (Year, Month Date Published). Website title. Retrieved from URL\n\nExample:\n" +
                "Satalkar, B. (2010, July 15). Water aerobics. Retrieved from http://www.buzzle.com");
        }

        private void bookKeyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Last, F. M. (Year Published) Book Title. City, State: Publisher.\n\nExample:\n" +
                "James, H. (1937). The ambassadors. New York, NY: Scribner.");
        }

        private void bookChapterKeyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Last, F. M. (Year Published). Title of chapter In F. M. Last Editor (Ed.), Title of book/anthology (pp. Pages). " +
                "Publisher City, State: Publisher.\n\nExample:\nServiss, G. P. (1911). A trip of terror. In A Columbus of space (pp. 17-32). " +
                "New York, NY: Appleton.");
        }

        private void journalKeyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Last, F. M., & Last, F. M. (Year Published). Article title. Journal Name, Volume(Issue), pp. Pages.\n\nExample:\n" +
                "Jacoby, W. G. (1994). Public attitudes toward government spending. American Journal of Political Science, 38(2), 336-361.");
        }

        private void onlineNewspaperKeyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Last, F. M. (Year, Month Date Published). Article title. Newspaper Title, Retrieved from URL.\n\nExample:\n" +
                "Bowman, L. (1990, March 7). Bills target Lake Erie mussels. The Pittsburgh Press, Retrieved from http://www.pittsburghpress.com");
        }
    }
}
