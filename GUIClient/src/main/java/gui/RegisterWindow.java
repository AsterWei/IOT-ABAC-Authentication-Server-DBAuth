package gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import service.ClientContext;
import service.ClientService;
import utils.Constants;
import utils.Utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Locale;

public class RegisterWindow {
    private final JFrame mainFrame;
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton submitButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private JScrollPane loggerScrollPane;
    private JLabel attributesLabel;
    private JTextArea attributesTextArea;
    private JScrollPane attributesJScrollPane;
    private JTextPane loggerTextPane;
    private JButton backButton;
    private ClientContext context;
    private JFrame clientFrame;

    public RegisterWindow(TestClient client) {
        this.mainFrame = new JFrame("Register");
        this.clientFrame = client.getMainFrame();
        this.context = client.getContext();
        this.loggerTextPane.setDocument(client.getLoggerTextPane().getStyledDocument());
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.dispose();
                clientFrame.setVisible(true);
                clientFrame.setEnabled(true);
                clientFrame.setFocusable(true);
                clientFrame.requestFocus();
            }
        });

        this.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                String attrs = attributesTextArea.getText();
                if (!Utils.hasText(username) || !Utils.hasText(password) || !Utils.hasText(attrs)) {
                    Utils.appendToPane(loggerTextPane, "err: incomplete register info\n", Color.red);
                    return;
                }
                String body;
                try {
                    body = ClientService.generateUserRegisterBody(username, password, attrs);
                } catch (JsonProcessingException jsonProcessingException) {
                    Utils.appendToPane(loggerTextPane, "err: JsonProcessingException\n", Color.red);
                    return;
                }
                assert Utils.hasText(body);
                Utils.appendToPane(loggerTextPane, "request sent, body:\n" + body + "\n", Color.black);
                CloseableHttpResponse response;
                try {
                    response = ClientService.sendHttpPostRequest(context.getClient(),
                            Constants.USER_REG_URL, body);
                } catch (IOException ioException) {
                    Utils.appendToPane(loggerTextPane, "err: send register info error\n", Color.red);
                    return;
                }
                assert response != null;
                if (response.getStatusLine().getStatusCode() == 200) {
                    Utils.appendToPane(loggerTextPane, "register success!\n", Color.blue);
                } else {
                    String responseBodyStr;
                    try {
                        responseBodyStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                    } catch (IOException ioException) {
                        Utils.appendToPane(loggerTextPane, "err: read response body exception\n", Color.red);
                        return;
                    }
                    assert Utils.hasText(responseBodyStr);
                    Utils.appendToPane(loggerTextPane, "register fail, returned body:\n" + responseBodyStr + "\n",
                            Color.red);
                }
            }
        });
    }

    public void run() {
        this.loggerTextPane.setEditable(false);
        this.mainFrame.setContentPane(this.mainPanel);
        this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setLocationRelativeTo(clientFrame);
        this.mainFrame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 9, new Insets(10, 20, 20, 20), -1,
                -1));
        usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        mainPanel.add(usernameLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usernameTextField = new JTextField();
        usernameTextField.setColumns(0);
        mainPanel.add(usernameTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 5,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1),
                new Dimension(300, -1), 0, false));
        passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        mainPanel.add(passwordLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordTextField = new JTextField();
        passwordTextField.setText("");
        mainPanel.add(passwordTextField, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 5,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1),
                new Dimension(300, -1), 0, false));
        submitButton = new JButton();
        submitButton.setText("submit");
        mainPanel.add(submitButton, new com.intellij.uiDesigner.core.GridConstraints(4, 6, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 8, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        titleLabel = new JLabel();
        Font titleLabelFont = this.$$$getFont$$$(null, -1, 18, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Please Register");
        mainPanel.add(titleLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 9,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH,
                com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(320, 40), null, 0,
                false));
        loggerScrollPane = new JScrollPane();
        mainPanel.add(loggerScrollPane, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 6,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), new Dimension(400, -1), 0, false));
        loggerTextPane = new JTextPane();
        loggerScrollPane.setViewportView(loggerTextPane);
        attributesLabel = new JLabel();
        attributesLabel.setText("Attributes");
        mainPanel.add(attributesLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        attributesJScrollPane = new JScrollPane();
        mainPanel.add(attributesJScrollPane, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 5,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, new Dimension(300, -1), 0, false));
        attributesTextArea = new JTextArea();
        attributesTextArea.setRows(5);
        attributesJScrollPane.setViewportView(attributesTextArea);
        backButton = new JButton();
        backButton.setText("back");
        mainPanel.add(backButton, new com.intellij.uiDesigner.core.GridConstraints(4, 5, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size :
                currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) :
                new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
