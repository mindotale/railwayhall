import presentation.pages.mainpage.MainPage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPage::new);
    }
}