package dayplanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.text.*;
import javax.swing.Timer;

/**
 * @author ********
 * @studentid ********
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 * @Description: GUI class, handles the JFrame
 */
public class DayPlanner extends JFrame {

    public static final int FRAMEHEIGHT = 600;
    public static final int FRAMEWIDTH = 700;
    public static final int SMALLX = 200;
    public static final int SMALLY = 100;
    private JPanel searchPanel;
    private JPanel addPanel;
    private JPanel calenderPanel;
    private JPanel basicPanel;
    private JPanel homePanel;
    private JFrame fileChooser;
    private JTextField addLocation;
    private JLabel locationLabel;
    private JComboBox typeSearch;
    private JComboBox typeAdd;
    private JTextArea addMessage;
    private JTextField addTitle;
    private JTextField addStartTime;
    private JTextField addEndTime;
    private JTextField addComment;
    private JTextArea searchMessage;
    private JTextField searchTitle;
    private JTextField searchStartTime;
    private JTextField searchEndTime;
    private DayPlannerHelper d;
    private JLabel fillers[];
    private JLabel fillers1;
    private String defIn;
    private String defOut;
    private String inFilePath;
    private String outFilePath;
    private JButton calender[][];
    private JLabel calLabel;
    private JLabel calYear;
    private JTextArea overlaps;

    private JTextField search;
    private JTextArea calenderOut;
    private Map<String, Integer> mappedMonths = new HashMap<String, Integer>();
    public static final String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final int DAYS[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private JTextField quickText;
    private JTextArea quickArea;
    private JButton showOverlap;
    private JButton currentTime;
    private JButton customTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String currentDate = dateFormat.format(new Date());
    private String saveDay = "";
    private int year = Integer.parseInt(currentDate.split("/")[0]);
    private int month = Integer.parseInt(currentDate.split("/")[1]) - 1;

    /**
     * window listener
     */
    private class CheckOnExit implements WindowListener {

        public void windowOpened(WindowEvent e) {

        }

        public void windowClosing(WindowEvent e) {
            ConfirmWindow c = new ConfirmWindow();
            c.setVisible(true);
        }

        public void windowClosed(WindowEvent e) {

        }

        public void windowIconified(WindowEvent e) {

        }

        public void windowDeiconified(WindowEvent e) {

        }

        public void windowActivated(WindowEvent e) {

        }

        public void windowDeactivated(WindowEvent e) {

        }
    }

    /**
     * New panel for file chooser
     */
    private class FileChooser extends JFrame implements ActionListener {

        private JCheckBox setDefault;
        private JRadioButton setInput, setOutput;
        private JFileChooser c;

        public FileChooser() {
            setSize(600, 400);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            setDefault = new JCheckBox("Set as default");
            setInput = new JRadioButton("Set as input file");
            setOutput = new JRadioButton("Set as output file");
            setInput.setSelected(true);
            ButtonGroup group = new ButtonGroup();
            group.add(setInput);
            group.add(setOutput);
            JPanel south = new JPanel();
            south.setLayout(new FlowLayout());
            south.add(setDefault);
            south.add(setInput);
            south.add(setOutput);
            south.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            c = new JFileChooser();
            c.setCurrentDirectory(new File(System.getProperty("user.dir")));
            c.addActionListener(this);
            add(c, BorderLayout.NORTH);
            add(south, BorderLayout.SOUTH);
            c.setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }

        public void actionPerformed(ActionEvent e) {
            String a = e.getActionCommand();
            if (a.equals("ApproveSelection")) {
                if (setInput.isSelected()) {
                    inFilePath = c.getCurrentDirectory() + "/" + c.getSelectedFile().getName();
                    if (setDefault.isSelected()) {
                        defIn = c.getCurrentDirectory() + "/" + c.getSelectedFile().getName();
                    }
                    try {
                        Scanner s = new Scanner(new File(inFilePath));
                        d.readAndLoad(s, false);
                        s.close();
                    } catch (FileNotFoundException f) {
                    }

                }
                if (setOutput.isSelected()) {
                    outFilePath = c.getCurrentDirectory() + "/" + c.getSelectedFile().getName();
                    if (setDefault.isSelected()) {
                        defOut = c.getCurrentDirectory() + "/" + c.getSelectedFile().getName();
                    }
                }
                dispose();
            }
            if (a.equals("CancelSelection")) {
                dispose();
            }
        }
    }

    /**
     * Confirm exit menu
     */
    private class ConfirmWindow extends JFrame implements ActionListener {

        public ConfirmWindow() {
            setSize(SMALLX, SMALLY);
            setLocationRelativeTo(null);
            //getContentPane().setBackground();
            setLayout(new BorderLayout());
            JLabel confirmLabel = new JLabel("Are you sure you want to exit?");
            add(confirmLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            //buttonPanel.setBackground(Color.ORANGE);
            buttonPanel.setLayout(new FlowLayout());

            JButton exitButton = new JButton("Yes");
            exitButton.addActionListener(this);
            buttonPanel.add(exitButton);
            JButton cancelButton = new JButton("No");
            cancelButton.addActionListener(this);
            buttonPanel.add(cancelButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();

            if (actionCommand.equals("Yes")) {
                file();
                System.exit(0);
            }

            if (actionCommand.equals("No")) {
                dispose();
            }

        }

    }

    /**
     * In charge of all actions when it comes to menu bar
     */
    private class CheckListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String a = e.getActionCommand();
            if (a.equals("Advanced Search")) {
                searchTitle.setText(quickText.getText());
                quickText.setText("");
                basicPanel.setVisible(false);
                searchPanel.setVisible(true);
            } else if (a.equals("Quick Search")) {
                calenderPanel.setVisible(false);
                searchPanel.setVisible(false);
                homePanel.setVisible(false);
                addPanel.setVisible(false);
                helpPanel.setVisible(false);
                basicPanel.setVisible(true);
                try {
                    quickArea.setText(d.search(quickText.getText(), "", "", '!'));
                } catch (BadTimeException b) {
                } catch (EmptyStringException m) {
                }
            } else if (a.equals("Add file")) {
                FileChooser r = new FileChooser();
                r.setVisible(true);
            } else if (a.equals("search")) {
                addPanel.setVisible(false);
                calenderPanel.setVisible(false);
                homePanel.setVisible(false);
                basicPanel.setVisible(false);
                searchPanel.setVisible(true);
                helpPanel.setVisible(false);
                searchEndTime.setText("");
                searchStartTime.setText("");
                searchTitle.setText("");
                searchMessage.setText("");
            } else if (a.equals("add")) {
                calenderPanel.setVisible(false);
                searchPanel.setVisible(false);
                homePanel.setVisible(false);
                basicPanel.setVisible(false);
                addPanel.setVisible(true);
                helpPanel.setVisible(false);
                addEndTime.setText("");
                addMessage.setText("");
                addLocation.setText("");
                addStartTime.setText("");
                addTitle.setText("");
                addComment.setText("");
            } else if (a.equals("calender")) {
                addPanel.setVisible(false);
                searchPanel.setVisible(false);
                homePanel.setVisible(false);
                basicPanel.setVisible(false);
                currentTime.setVisible(false);
                customTime.setVisible(false);
                searchForNext.setVisible(false);
                custom.setVisible(false);
                plus.setVisible(false);
                minus.setVisible(false);
                go.setVisible(false);
                helpPanel.setVisible(false);
                calLabel.setText(MONTHS[month] + "/" + year);
                calenderPanel.setVisible(true);
            } else if (a.equals("help")) {
                calenderPanel.setVisible(false);
                searchPanel.setVisible(false);
                homePanel.setVisible(false);
                basicPanel.setVisible(false);
                addPanel.setVisible(false);
                helpPanel.setVisible(true);
            }else if (a.equals("Back to Menu")){
                calenderPanel.setVisible(false);
                searchPanel.setVisible(false);
                homePanel.setVisible(true);
                basicPanel.setVisible(false);
                addPanel.setVisible(false);
                helpPanel.setVisible(false);
            } else if (a.equals("quit")) {
                file();
                System.exit(0);
            }

        }
    }

    private class OverlappingSearch extends JFrame {

        /**
         * JFrame for overlap when searching
         */
        public OverlappingSearch() {
            super();
            setSize(600, 400);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(2, 1));
            setTitle("Overlaps");
            overlaps = new JTextArea(0, 0);
            ImageIcon image = new ImageIcon("warning.gif");
            JScrollPane scroll = new JScrollPane(overlaps);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            add(scroll, BorderLayout.SOUTH);
            overlaps.setEditable(false);
            JLabel gif = new JLabel(image);
            gif.setSize(100, 100);
            add(gif, BorderLayout.NORTH);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(false);
        }
    }

    private class CalenderOutput extends JFrame {

        /**
         * Generates jframe with textarea
         */
        public CalenderOutput() {
            super();
            setSize(400, 400);
            setLocationRelativeTo(null);
            setLayout(new GridLayout());
            calenderOut = new JTextArea();
            JScrollPane scroll = new JScrollPane(calenderOut);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            add(scroll);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(false);
        }
    }

    /**
     * Prints to default file and temporary file
     */
    private void file() {
        PrintWriter def = null;
        try {
            def = new PrintWriter(new File(defOut));
        } catch (FileNotFoundException f) {
            JOptionPane.showMessageDialog(null, "Default File not found!");
            defOut = "";
        }

        try {

            PrintWriter p = new PrintWriter(new File("default.txt"));
            p.println(defIn);
            p.println(defOut);
            p.close();
        } catch (FileNotFoundException e) {
            //System.out.println(0);
            JOptionPane.showMessageDialog(null, "File not found!");
        }

        if (!defOut.isEmpty()) {
            try {
                d.writeToFile(def);
                def.close();
            } catch (BadTimeException bte) {
            } catch (EmptyStringException e) {

            }

        }
        if (!outFilePath.isEmpty()) {
            try {
                PrintWriter p = new PrintWriter(new File(outFilePath));
                d.writeToFile(p);

                p.close();

            } catch (FileNotFoundException ffe) {
            } catch (BadTimeException be) {
            } catch (EmptyStringException f) {

                JOptionPane.showMessageDialog(null, f + "File not found!");
            }
        }

    }
    private String overlap;

    /**
     * In charge of components for search panel
     */
    private class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String a = e.getActionCommand();
            String b = (String) typeSearch.getSelectedItem();
            char activity;

            if (b.equals("school")) {
                activity = 's';
            } else if (b.equals("home")) {
                activity = 'h';
            } else if (b.equals("other")) {
                activity = 'o';
            } else {
                activity = '!';
            }

            if (a.equals("Show Overlaps")) {

                OverlappingSearch o = new OverlappingSearch();
                overlaps.setText("Overlapping activities: \n\n" + overlap);
                o.setVisible(true);
                showOverlap.setVisible(false);
            } else if (a.equals("Enter")) {
                overlap = "";
                try {
                    searchMessage.setText(d.search(searchTitle.getText(), searchStartTime.getText(), searchEndTime.getText(), activity));
                    if (!searchStartTime.getText().isEmpty() && !searchEndTime.getText().isEmpty()) {
                        overlap = d.getOverlaps(new Time(searchStartTime.getText()), new Time(searchEndTime.getText()));
                    } else if (!searchStartTime.getText().isEmpty() && searchEndTime.getText().isEmpty()) {
                        overlap = d.getOverlaps(new Time(searchStartTime.getText()), null);
                    } else if (searchStartTime.getText().isEmpty() && !searchEndTime.getText().isEmpty()) {
                        overlap = d.getOverlaps(null, new Time(searchEndTime.getText()));
                    }

                } catch (BadTimeException bte) {
                } catch (EmptyStringException c) {
                }
                if (!overlap.isEmpty()) {
                    showOverlap.setVisible(true);
                }
            } else if (a.equals("Reset")) {
                searchEndTime.setText("");
                searchStartTime.setText("");
                searchTitle.setText("");
                searchMessage.setText("");
            }

        }
    }

    /**
     * In charge of components for add panel
     */
    private class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String a = e.getActionCommand();
            String s = (String) typeAdd.getSelectedItem();
            char activity;

            if (s.equals("other")) {
                activity = 'o';

            } else if (s.equals("home")) {
                activity = 'h';

            } else {
                activity = 's';
            }

            if (a.equals("Enter")) {
                addMessage.setText(d.addInput(addTitle.getText(), addStartTime.getText(), addEndTime.getText(), addComment.getText(), addLocation.getText(), activity));
            } else if (a.equals("Reset")) {
                addComment.setText("");
                addEndTime.setText("");
                addMessage.setText("");
                addLocation.setText("");
                addStartTime.setText("");
                addTitle.setText("");

            }
        }
    }

    /**
     * Combo box listener for add
     */
    private class ComboListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            String a = (String) cb.getSelectedItem();

            if (a.equals("other")) {
                addLocation.setVisible(true);
                locationLabel.setVisible(true);

            } else if (a.equals("school")) {
                addLocation.setVisible(false);
                locationLabel.setVisible(false);

            } else if (a.equals("home")) {
                addLocation.setVisible(false);
                locationLabel.setVisible(false);

            }
        }
    }

    public static void main(String[] args) throws BadTimeException {

        DayPlanner window = new DayPlanner();
        window.setVisible(true);
    }

    /**
     * Sets up search panel
     */
    private void setUpSearch() {

        //SEARCH PANEL
        searchPanel = new JPanel(new BorderLayout());
        searchMessage = new JTextArea(15, 0);
        searchMessage.setEditable(false);
        showOverlap = new JButton("Show Overlaps");

        showOverlap.addActionListener(new SearchListener());
        JScrollPane scroll = new JScrollPane(searchMessage);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //JPanel searchEast = new JPanel(new GridLayout(4, 3, 0, 20));
        JPanel searchEast = new JPanel(new GridLayout(2, 1));
        JPanel searchSouth = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Search Activities:"), BorderLayout.NORTH);
        JPanel searchWest = new JPanel();
        // searchWest.setLayout(new BoxLayout(searchWest, BoxLayout.Y_AXIS));
        searchWest.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 10, 10);

        String types[] = {"school", "home", "other", ""};
        typeSearch = new JComboBox(types);
        typeSearch.setSelectedIndex(3);

        Font font = new Font("Courier", Font.PLAIN, 15);
        searchStartTime = new JTextField(40);
        searchEndTime = new JTextField(40);
        searchTitle = new JTextField(40);
        searchStartTime.setFont(font);
        searchEndTime.setFont(font);
        searchTitle.setFont(font);

        //GridBagLayout
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        searchWest.add(new JLabel("    Type:"), c);
        c.gridy = 0;
        c.gridx = 1;
        c.gridwidth = 3;
        searchWest.add(typeSearch, c);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        searchWest.add(new JLabel("    Title:"), c);
        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 3;
        searchWest.add(searchTitle, c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridwidth = 6;
        searchWest.add(new JLabel("Starting time (of the form YYYY:MM:DD:HH:mm)"), c);
        c.gridy = 3;
        c.gridx = 0;
        searchWest.add(searchStartTime, c);
        c.gridy = 4;
        c.gridx = 0;
        searchWest.add(new JLabel("Ending time (of the form YYYY:MM:DD:HH:mm)"), c);
        c.gridx = 0;
        c.gridy = 5;
        searchWest.add(searchEndTime, c);
        c.gridwidth = 1;
        c.gridy = 6;
        c.gridx = 0;
        searchWest.add(showOverlap, c);
        showOverlap.setVisible(false);

        searchSouth.add(new JLabel("Search Results:"), BorderLayout.NORTH);
        searchSouth.add(scroll, BorderLayout.SOUTH);
        JButton searchReset;
        JButton searchEnter;
        Dimension d = new Dimension(90, 60);
        searchReset = new JButton("Reset");
        searchEnter = new JButton("Enter");
        searchReset.setPreferredSize(d);
        searchEnter.setPreferredSize(d);
        searchReset.addActionListener(new SearchListener());
        searchEnter.addActionListener(new SearchListener());
        searchEast.add(searchReset);
        searchEast.add(searchEnter);

        searchPanel.add(searchWest, BorderLayout.WEST);
        searchPanel.add(searchSouth, BorderLayout.SOUTH);
        searchPanel.add(searchEast, BorderLayout.EAST);
        searchPanel.setVisible(false);

        add(searchPanel);
    }

    /**
     * Sets up add panel
     */
    private void setUpAdd() {
        Dimension d = new Dimension(90, 60);
        //ADD PANEL
        addPanel = new JPanel(new BorderLayout());
        addMessage = new JTextArea(15, 0);
        addMessage.setEditable(false);
        JScrollPane scroll = new JScrollPane(addMessage);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel addSouth = new JPanel(new BorderLayout());
        addSouth.add(new JLabel("Messages:"), BorderLayout.NORTH);
        addSouth.add(scroll, BorderLayout.SOUTH);
        JButton addReset;
        JButton addEnter;
        addReset = new JButton("Reset");
        addEnter = new JButton("Enter");
        addReset.setPreferredSize(d);
        addEnter.setPreferredSize(d);
        addEnter.addActionListener(new AddListener());
        addReset.addActionListener(new AddListener());
        addPanel.setVisible(false);
        String types[] = {"other", "school", "home"};
        typeAdd = new JComboBox(types);
        typeAdd.setSelectedIndex(2);
        typeAdd.addActionListener(new ComboListener());

        JPanel addEast = new JPanel(new GridLayout(2, 1));
        addEast.add(addReset);
        addEast.add(addEnter);
        fillers1 = new JLabel(" ");

        JPanel addWest = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 10, 10);

        locationLabel = new JLabel("Location:");
        addLocation = new JTextField(40);
        addComment = new JTextField(40);
        addEndTime = new JTextField(40);
        addStartTime = new JTextField(40);
        addTitle = new JTextField(40);
        c.gridy = 0;
        c.gridx = 0;
        addWest.add(new JLabel("Type:"), c);
        c.gridx = 1;
        c.gridwidth = 4;
        addWest.add(typeAdd, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        addWest.add(new JLabel("Title:"), c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        addWest.add(addTitle, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        addWest.add(new JLabel("Starting time (of the form YYYY:MM:DD:HH:mm)"), c);
        c.gridy = 3;
        addWest.add(addStartTime, c);
        c.gridy = 4;
        addWest.add(new JLabel("Ending time (of the form YYYY:MM:DD:HH:mm)"), c);
        c.gridy = 5;
        addWest.add(addEndTime, c);
        c.gridwidth = 1;
        c.gridy = 6;
        addWest.add(locationLabel, c);
        c.gridx = 1;
        c.gridwidth = 4;
        addWest.add(addLocation, c);
        addLocation.setVisible(false);
        locationLabel.setVisible(false);
        c.gridy = 7;
        c.gridx = 0;
        c.gridwidth = 1;
        addWest.add(new JLabel("Comment:"), c);
        c.gridx = 1;
        c.gridwidth = 4;
        addWest.add(addComment, c);
        addPanel.add(addSouth, BorderLayout.SOUTH);
        addPanel.add(addEast, BorderLayout.EAST);
        addPanel.add(new JLabel("Adding an Activity:"), BorderLayout.NORTH);
        addPanel.add(addWest, BorderLayout.WEST);
        add(addPanel);
    }

    private JLabel clock;
    /**
     * class encapsulated into instance
     */
    private ActionListener clock1 = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String text[] = clock.getText().split(" ")[1].split(":");
            int hours = Integer.parseInt(text[0]);
            int min = Integer.parseInt(text[1]);
            int sec = Integer.parseInt(text[2]);
            sec = (sec + 1) % 60;
            if (sec == 0) {
                min = (min + 1) % 60;
                if (min == 0) {
                    hours = (hours + 1) % 24;
                }
            }

            clock.setText(currentDate + ", " + (hours / 10 == 0 ? "0" + hours : hours) + ":" + (min / 10 == 0 ? "0" + min : min) + ":" + (sec / 10 == 0 ? "0" + sec : sec));
        }
    };

    /**
     * Sets up home panel
     */
    private void setUpHome() {
        //HOME PANEL
        homePanel = new JPanel();

        Font font = new Font("Courier", Font.BOLD, 15);
        JLabel home = new JLabel("<html>Welcome to my Dayplanner!<br>"
                + "Choose a command from the \"command\" menu above for adding<br>"
                + "an activity, searching activities, adding an output/input file,<br>"
                + "using the calender for searching activities, looking for in depth help,<br>"
                + "or quitting the program.</html>");
        home.setAlignmentY(TOP_ALIGNMENT);
        home.setFont(font);
        homePanel.setLayout(new BorderLayout());
        homePanel.setVisible(true);
        home.setHorizontalAlignment(JLabel.CENTER);
        home.setVerticalAlignment(JLabel.CENTER);
        homePanel.add(home, BorderLayout.CENTER);
        //homePanel.add(clock,BorderLayout.NORTH);
        add(homePanel);
    }

    /**
     * Checks if leap year
     *
     * @param year
     * @return
     */
    private boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            if (year % 100 != 0) {
                return true;
            }
        } else if (year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Initializes calender
     *
     * @param month
     * @param year
     */
    private void initCalender(int month, int year) {
        int c = 0;
        int m[] = {0, 3, 3, 6, 1, 4, 5, 2, 5, 0, 3, 5};
        if (isLeapYear(year)) {
            m[0] = 6;
            m[1] = 2;
        }
        int r = year / 100;
        if (r % 4 == 0) {
            c = 6;
        }
        if ((r - 3) % 4 == 0 || (r + 3) % 4 == 0) {
            c = 0;
        }
        if ((r - 2) % 4 == 0 || (r + 2) % 4 == 0) {
            c = 2;
        }
        if ((r - 1) % 4 == 0 || (r + 1) % 4 == 0) {
            c = 4;
        }
        int firstDay = (1 + m[month] + (year % 100) + (year % 100) / 4 + c) % 7;

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                calender[i][j].setText("");
            }
        }
        for (int i = 0; i < (isLeapYear(year) ? DAYS[month] + 1 : DAYS[month]); ++i) {
            if (d.isTimeIn(year, month, i)) {
                calender[(firstDay) / 7][firstDay % 7].setText((i + 1) + "*");
            } else {
                calender[(firstDay) / 7][firstDay % 7].setText((i + 1) + "");
            }
            firstDay++;
        }
    }

    /**
     * Responds to calender events
     */
    private class CalenderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String a = e.getActionCommand();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
            String labelText = calLabel.getText();
            year = Integer.parseInt(labelText.split("/")[1]);
            month = mappedMonths.get(labelText.split("/")[0]);
            if (a.contains("*")) {
                currentTime.setVisible(false);
                customTime.setVisible(false);
                searchForNext.setVisible(false);
                custom.setVisible(false);
                plus.setVisible(false);
                minus.setVisible(false);
                go.setVisible(false);
                /* customTime.setSelected(false);
                 currentTime.setSelected(false);*/
                String buttonText = a.substring(0, a.length() - 1);
                int day = Integer.parseInt(buttonText);

                CalenderOutput out = new CalenderOutput();
                out.setVisible(true);
                calenderOut.setEditable(false);
                calenderOut.setText("All activities on " + MONTHS[month] + " " + day + ", " + year + "\n\n" + d.getAllActivityInTime(year, month, day));
            }
            try {
                Integer.parseInt(a);
                if (a.length() < 3 && !a.contains("*")) {
                    saveDay = a;
                    currentTime.setVisible(true);
                    customTime.setVisible(true);
                    searchForNext.setVisible(true);
                }
            } catch (NumberFormatException nfe) {

            }

            if (a.equals("Next")) {
                month = (month + 1) % 12;

                if (month == 0) {
                    year++;
                }
                initCalender(month, year);
                calLabel.setText(MONTHS[month] + "/" + year);
            } else if (a.equals("Previous")) {
                if (year > 0 || month > 0) {
                    month--;
                    if (month < 0) {
                        month = 11;
                    }
                    if (month == 11) {
                        year--;
                    }
                    calLabel.setText(MONTHS[month] + "/" + year);
                    initCalender(month, year);
                }

            } else if (a.equals("Get")) {
                Object output[];
                boolean isValid;
                int years;
                int months;
                try {
                    output = d.isValidCalender(search.getText());
                    isValid = (Boolean) output[0];
                    years = (Integer) output[2];
                    months = (Integer) output[1];
                    if (isValid) {
                        if (months == 0 && years != 0) {
                            initCalender(month, years);
                            calLabel.setText(MONTHS[month] + "/" + years);
                        } else if (months != 0) {
                            initCalender(months - 1, years);
                            calLabel.setText(MONTHS[months - 1] + "/" + years);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid String entered!");

                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "You didn't enter a valid date!");
                } catch (EmptyStringException ese) {
                    JOptionPane.showMessageDialog(null, "You have to have exactly 2 arguments!");
                }
                search.setText("");
            } else if (a.equals("Use Current Time")) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String startDate = year + ":" + (month + 1) + ":" + saveDay + ":" + sdf.format(c.getTime()).split(":")[0] + ":" + sdf.format(c.getTime()).split(":")[1];

                addStartTime.setText(startDate);
                addPanel.setVisible(true);
                calenderPanel.setVisible(false);
                currentTime.setVisible(false);
                customTime.setVisible(false);
                //currentTime.setSelected(false);
                searchForNext.setVisible(false);
                // customTime.setSelected(false);
            } else if (a.equals("Use Custom Time")) {
                currentTime.setVisible(false);
                customTime.setVisible(false);
                searchForNext.setVisible(false);
                /*searchForNext.setSelected(false);*/
                custom.setVisible(true);
                plus.setVisible(true);
                minus.setVisible(true);
                go.setVisible(true);

                String l = sd.format(cal.getTime());
                custom.setText(l);
            } else if (a.equals("+")) {
                String text[] = custom.getText().split(":");
                try {
                    if (text.length < 2) {
                        throw new NumberFormatException();
                    }
                    int hour = Integer.parseInt(text[0]);
                    int minute = Integer.parseInt(text[1]);
                    if (hour > 23 || hour < 0) {
                        throw new NumberFormatException();
                    }
                    if (minute < 0 || minute > 59) {
                        throw new NumberFormatException();
                    }
                    minute = (minute + 1) % 60;
                    if (minute == 0) {
                        hour = (hour + 1) % 24;
                    }
                    custom.setText((hour / 10 == 0 ? "0" + hour : hour) + ":" + (minute / 10 == 0 ? "0" + minute : minute));
                } catch (NumberFormatException ex) {
                    custom.setText(sd.format(cal.getTime()));
                }

            } else if (a.equals("-")) {
                String text1[] = custom.getText().split(":");
                try {
                    if (text1.length < 2) {
                        throw new NumberFormatException();
                    }
                    int hour1 = Integer.parseInt(text1[0]);
                    int minute1 = Integer.parseInt(text1[1]);
                    if (hour1 > 23 || hour1 < 0) {
                        throw new NumberFormatException();
                    }
                    if (minute1 < 0 || minute1 > 59) {
                        throw new NumberFormatException();
                    }
                    minute1--;
                    if (minute1 < 0) {
                        minute1 = 59;
                        hour1--;
                        if (hour1 < 0) {
                            hour1 = 23;
                        }
                    }
                    custom.setText((hour1 / 10 == 0 ? "0" + hour1 : hour1) + ":" + (minute1 / 10 == 0 ? "0" + minute1 : minute1));
                } catch (NumberFormatException ex) {
                    custom.setText(sd.format(cal.getTime()));
                }

            } else if (a.equals("Go")) {

                String t[] = custom.getText().split(":");
                if (t.length == 2) {
                    try {
                        int h = Integer.parseInt(t[0]);
                        int m = Integer.parseInt(t[1]);
                        custom.setVisible(false);
                        plus.setVisible(false);
                        String sDate = year + ":" + (month + 1) + ":" + saveDay + ":" + h + ":" + m;
                        minus.setVisible(false);
                        go.setVisible(false);
                        addStartTime.setText(sDate);
                        addPanel.setVisible(true);
                        calenderPanel.setVisible(false);
                        currentTime.setVisible(false);
                        customTime.setVisible(false);
                        /* currentTime.setSelected(false);
                         customTime.setSelected(false);*/

                    } catch (NumberFormatException n) {
                        custom.setText(sd.format(cal.getTime()));
                    }
                } else {
                    custom.setText(sd.format(cal.getTime()));
                }
            } else if (a.equals("Next upcoming activity")) {
                JOptionPane.showMessageDialog(null, d.getNextActivity(year, month + 1, Integer.parseInt(saveDay)));

            }
        }

    }

    private JTextField custom;
    private JButton plus = new JButton("+");
    private JButton minus = new JButton("-");
    private JButton go = new JButton("Go");
    private JButton searchForNext;

    /**
     * Sets up the calender panel
     */
    private void setUpCalender() {
        calenderPanel = new JPanel();
        calenderPanel.setVisible(false);
        calenderPanel.setLayout(new BorderLayout());
        JPanel calenderNorth = new JPanel(new BorderLayout());
        JPanel calenderSouth = new JPanel(new GridBagLayout());
        JPanel calenderWest = new JPanel(new GridBagLayout());
        currentTime = new JButton("Use Current Time");
        customTime = new JButton("Use Custom Time");
        JButton next = new JButton("Next");
        JButton prev = new JButton("Previous");
        next.setPreferredSize(new Dimension(100, 25));
        prev.setPreferredSize(new Dimension(100, 25));
        next.addActionListener(new CalenderListener());
        prev.addActionListener(new CalenderListener());
        search = new JTextField(10);
        JButton search1 = new JButton("Get");
        search1.addActionListener(new CalenderListener());

        searchForNext = new JButton("Next upcoming activity");
        searchForNext.addActionListener(new CalenderListener());
        JPanel calenderSNorth = new JPanel(new GridLayout(1, 7));
        JPanel calenderNNorth = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        b.fill = GridBagConstraints.HORIZONTAL;
        b.insets = new Insets(0, 5, 0, 5);
        calLabel = new JLabel("");
        calLabel.setFont(new Font("Courier", Font.BOLD, 15));

        calYear = new JLabel("Search (YYYY/MM):");
        b.gridy = 0;
        b.gridx = 0;
        calenderNNorth.add(calYear, b);
        b.gridx = 1;
        calenderNNorth.add(search, b);
        b.gridx = 2;
        calenderNNorth.add(search1, b);
        b.gridx = 3;
        calenderNNorth.add(prev, b);
        b.gridx = 4;
        calenderNNorth.add(calLabel, b);
        b.gridx = 5;
        calenderNNorth.add(next, b);
        calenderNorth.add(calenderNNorth, BorderLayout.NORTH);

        JLabel weekdays[] = {new JLabel("    Sun."), new JLabel("    Mon."), new JLabel("    Tue."), new JLabel("    Wed."), new JLabel("    Thurs."), new JLabel("    Fri."), new JLabel("    Sat.")};

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);

        for (int i = 0; i < 7; ++i) {
            calenderSNorth.add(weekdays[i]);
        }
        calenderSNorth.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        calenderNorth.add(calenderSNorth, BorderLayout.SOUTH);
        c.insets = new Insets(0, 0, 0, 0);
        calender = new JButton[6][7];
        Dimension d = new Dimension(100, 80);
        for (int i = 1; i <= 6; ++i) {
            for (int j = 1; j <= 7; ++j) {
                calender[i - 1][j - 1] = new JButton("");
                calender[i - 1][j - 1].setPreferredSize(d);
                calender[i - 1][j - 1].setHorizontalAlignment(SwingConstants.LEFT);
                calender[i - 1][j - 1].setVerticalAlignment(SwingConstants.TOP);
                calender[i - 1][j - 1].setOpaque(false);
                calender[i - 1][j - 1].setContentAreaFilled(false);
                calender[i - 1][j - 1].setBorderPainted(false);
                calender[i - 1][j - 1].addActionListener(new CalenderListener());
                c.gridx = j - 1;
                c.gridy = i - 1;
                calenderWest.add(calender[i - 1][j - 1], c);
            }
        }
        c.gridx = 0;
        c.gridy += 1;
        c.gridwidth = 2;
        calenderWest.add(currentTime, c);
        c.gridx = 2;
        calenderWest.add(customTime, c);
        c.gridx = 4;
        calenderWest.add(searchForNext, c);

        currentTime.addActionListener(new CalenderListener());
        customTime.addActionListener(new CalenderListener());
        currentTime.setVisible(false);
        customTime.setVisible(false);
        searchForNext.setVisible(false);
        c.gridy += 1;
        c.gridx = 0;
        c.gridwidth = 1;

        custom = new JTextField(10);
        plus.addActionListener(new CalenderListener());
        minus.addActionListener(new CalenderListener());
        go.addActionListener(new CalenderListener());

        minus.setPreferredSize(new Dimension(0, 18));
        c.insets = new Insets(0, 50, 0, 0);
        calenderWest.add(minus, c);
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 0);
        calenderWest.add(custom, c);
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 50);
        plus.setPreferredSize(new Dimension(0, 18));
        calenderWest.add(plus, c);
        c.gridx += 1;
        c.insets = new Insets(0, 10, 0, 10);

        calenderWest.add(go, c);
        minus.setVisible(false);
        custom.setVisible(false);
        plus.setVisible(false);
        go.setVisible(false);
        initCalender(month, year);
        calenderPanel.add(calenderNorth, BorderLayout.NORTH);
        calenderPanel.add(calenderWest, BorderLayout.WEST);

        add(calenderPanel);
        //pack();
    }

    /**
     * Sets up the basic search panel
     */
    private void setUpBasic() {
        basicPanel = new JPanel(new BorderLayout());
        JButton advancedSearch = new JButton("Advanced Search");
        advancedSearch.setPreferredSize(new Dimension(200, 60));
        advancedSearch.addActionListener(new CheckListener());
        quickArea = new JTextArea(31, 0);
        quickArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(quickArea);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel south = new JPanel(new GridLayout(1, 4));
        south.add(advancedSearch);
        south.add(new JLabel(""));
        south.add(new JLabel(""));
        south.add(new JLabel(""));
        basicPanel.add(south, BorderLayout.SOUTH);
        basicPanel.add(scroll, BorderLayout.NORTH);
        basicPanel.setVisible(false);
        add(basicPanel);
    }

    private JPanel helpPanel;

    private void setUpHelp() {
        JButton back = new JButton("Back to Menu");
        String output = "";
        output += "<html>Add file:<br>";
        output += "- When the add file option is selected a file browsing interface will appear,<br>";
        output += "  you will also have a checkbox and 2 radiobuttons. These toggle whether your<br>";
        output += "  file is input or output and/or if it is a default file. Default files<br>";
        output += "  are written to and read from without prompting the user again.<br><br>";
        output += "Search:<br>";
        output += "- Pretty self explanatory, enter information in the textfields<br>";
        output += "  as prompted, if there are activities that exactly match your<br>";
        output += "  search criteria, they will be outputting into the text area,<br>";
        output += "  if there are any activities that overlap with your search<br>";
        output += "  a button will appear that if you press will let you see<br>";
        output += "  the overlapped activities.<br><br>";
        output += "Add:<br>";
        output += "- Again pretty self explanatory just enter the details of the<br>";
        output += "  activities you want to add, then press enter.<br><br>";
        output += "Calendar:<br>";
        output += "- Calender is initialized to December 2014, if give day has<br>";
        output += "  an activity, the day number will have a * next to it, if you<br>";
        output += "  want to see those activities, press press that number.<br>";
        output += "  If you press any day number that has no * next to it you<br>";
        output += "  three new components will appear, two of them will redirect<br>";
        output += "  you to the add panel, with the current date you selected, the other<br>";
        output += "  will display the next day that you have activities. To search for a <br>";
        output += "  specific month/year, input the information into the textbox on the top<br>";
        output += "  left, to browse through months, use the previous and next buttons<br><br>";
        output += "Basic Search:<br>";
        output += "- At any time in the program you can use the basic search function<br>";
        output += "  to use this function, just type in the keywords you want to search<br>";
        output += "  in the textfield at the top of the frame, then press go, if you<br>";
        output += "  want to refine this search, press advanced search, and you will<br>";
        output += "  be put into the regular search panel with your keywords.</html>";
        helpPanel = new JPanel(new BorderLayout());
        JLabel helpLabel = new JLabel(output);
       // helpLabel.setFont(new Font("Courier",Font.BOLD,10));
        helpPanel.add(helpLabel,BorderLayout.NORTH);
        back.addActionListener(new CheckListener());
        helpPanel.add(back,BorderLayout.SOUTH);
        helpPanel.setVisible(false);
        add(helpPanel);
    }

    /**
     * Instantiates the GUI
     */
    public DayPlanner() {
        super();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        clock = new JLabel(currentDate + ", " + sdf.format(cal.getTime()));
        Timer t = new Timer(1000, clock1);
        t.start();

        for (int i = 0; i < 12; ++i) {
            mappedMonths.put(MONTHS[i], i);
        }
        defIn = "";
        defOut = "";

        inFilePath = "";
        outFilePath = "";
        d = new DayPlannerHelper();
        try {
            Scanner s = new Scanner(new File("default.txt"));
            defIn = s.nextLine();
            defOut = s.nextLine();
            Scanner s1 = new Scanner(new File(defIn));

            if (!defIn.isEmpty()) {
                d.readAndLoad(s1, true);
            }
            s.close();
            s1.close();

        } catch (FileNotFoundException f) {
            defIn = "";
            JOptionPane.showMessageDialog(null, "Default text file not found!");
        }
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        setLocationRelativeTo(null);
        //this.setResizable(false);
        setTitle("Dayplanner");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        JButton quickSearch = new JButton("Quick Search");
        quickSearch.addActionListener(new CheckListener());
        quickText = new JTextField(10);
        String dropMenu[] = {"Add file", "search", "add", "calender", "help", "quit"};
        JMenu menu = new JMenu("Command");
        menu.setBackground(Color.LIGHT_GRAY);
        menu.setOpaque(true);
        JMenuItem[] j = new JMenuItem[dropMenu.length];
        for (int i = 0; i < dropMenu.length; ++i) {
            j[i] = new JMenuItem(dropMenu[i]);
            j[i].addActionListener(new CheckListener());
            menu.add(j[i]);
        }
        JMenuBar drop = new JMenuBar();
        drop.add(menu);
        drop.add(quickText);
        drop.add(quickSearch);
        drop.add(clock);
        t.start();
        setJMenuBar(drop);

        setUpCalender();
        setUpHome();
        setUpSearch();
        setUpAdd();
        setUpBasic();
        setUpHelp();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CheckOnExit());
    }
}
