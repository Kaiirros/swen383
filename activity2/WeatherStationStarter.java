import java.awt.* ;
 import java.awt.event.* ;
 
public class WeatherStationStarter extends Frame {

    /*
     * When a WeatherStationStarter object is created;
     *      Create frame, layout and styles
     *      Create button menu
     *      Add window close listener
     *      Pack and set visible
     */

    public WeatherStationStarter() {
        super("Weather Station");
        setBackground(Color.DARK_GRAY);
        setSize(500, 400);
        setLayout(new GridLayout(3, 1));
        Label title = new Label("Welcome to the Weather Station");
        title.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        title.setForeground(Color.white);
        add(title);

        createMenu();

        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }        
            });

        pack();
        setVisible(true);  

    }

    /*
     * The creation of the 'home' menu for the weather service application.
     * It includes the creation of three buttons 'AWTUI', 'SwingUI', and 'Console' 
     * that will launch each respective application when the user clicks them
     * 
     *      Creates buttons
     *      Adds actionListeners to buttons
     *      Adds buttons to main frame
     */
    
    private void createMenu(){
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1, 3));
        Button awtUIButton = new Button("AWTUI");
        Button swingUIButton = new Button("SwingUI");
        Button consoleButton = new Button("Console");


        //AWTUI button action listener
        awtUIButton.addActionListener(e -> {
            System.out.println("AWTUI");
            new AWTUI();
            this.dispose();
        });

        //SwingUI button action listener
        swingUIButton.addActionListener(e -> {
            System.out.println("Swing");
            new SwingUI();
            this.dispose();
        });

        //Console button action listener
        consoleButton.addActionListener(e -> {
            System.out.println("Console");
            new WeatherStationConsole();
            this.dispose();
        });

        panel.add(awtUIButton);
        panel.add(swingUIButton);
        panel.add(consoleButton);

        add(panel);
    }

    /*
     * Initial main method.
     *      Runs the WeatherStationStarter file
     */
    public static void main(String[] args) {
        new WeatherStationStarter() ;
    }
}
