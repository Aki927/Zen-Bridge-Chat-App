package com.cs.zenbridgechat;

//*********************************************************************
//	Jerome Laranang, T00635622
//
//	COMP 2161 Final Project, Zen Bridge Chat Application, December 7, 2024
//
//  This Android program is a chat application where therapists, patients,
//  and Zen Bridge members can connect and have a counselling sessions via messaging.
//  It uses Firebase Authentication using phone number for login including OTP
//  verification. The user data, chatroom data and bug report/feedback data are stored
//  in Firestore Database.
//*********************************************************************

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CopyrightActivity extends AppCompatActivity {
    WebView webView;
    CheckBox acceptCheckbox;
    Button proceedButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_copyright);

        webView = findViewById(R.id.disclaimer_web_view);
        acceptCheckbox = findViewById(R.id.accept_checkbox);
        proceedButton = findViewById(R.id.proceed_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Load HTML/CSS content in a form of a string to be loaded into the WebView
        String htmlContent = "<style>\n" +
                "  [data-custom-class='body'], [data-custom-class='body'] * {\n" +
                "          background: transparent !important;\n" +
                "        }\n" +
                "[data-custom-class='title'], [data-custom-class='title'] * {\n" +
                "          font-family: Arial !important;\n" +
                "font-size: 70px !important;\n" +
                "color: #000000 !important;\n" +
                "        }\n" +
                "[data-custom-class='subtitle'], [data-custom-class='subtitle'] * {\n" +
                "          font-family: Arial !important;\n" +
                "color: #595959 !important;\n" +
                "font-size: 60px !important;\n" +
                "        }\n" +
                "[data-custom-class='heading_1'], [data-custom-class='heading_1'] * {\n" +
                "          font-family: Arial !important;\n" +
                "font-size: 60px !important;\n" +
                "color: #000000 !important;\n" +
                "        }\n" +
                "[data-custom-class='heading_2'], [data-custom-class='heading_2'] * {\n" +
                "          font-family: Arial !important;\n" +
                "font-size: 50px !important;\n" +
                "color: #000000 !important;\n" +
                "        }\n" +
                "[data-custom-class='body_text'], [data-custom-class='body_text'] * {\n" +
                "          color: #595959 !important;\n" +
                "font-size: 50px !important;\n" +
                "font-family: Arial !important;\n" +
                "        }\n" +
                "[data-custom-class='link'], [data-custom-class='link'] * {\n" +
                "          color: #3030F1 !important;\n" +
                "font-size: 50px !important;\n" +
                "font-family: Arial !important;\n" +
                "word-break: break-word !important;\n" +
                "        }\n" +
                "</style>\n" +
                "      <span style=\"display: block;margin: 0 auto 3.125rem;width: 11.125rem;height: 2.375rem;background: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxNzgiIGhlaWdodD0iMzgiIHZpZXdCb3g9IjAgMCAxNzggMzgiPgogICAgPGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj4KICAgICAgICA8cGF0aCBmaWxsPSIjRDFEMUQxIiBkPSJNNC4yODMgMjQuMTA3Yy0uNzA1IDAtMS4yNTgtLjI1Ni0xLjY2LS43NjhoLS4wODVjLjA1Ny41MDIuMDg2Ljc5Mi4wODYuODd2Mi40MzRILjk4NXYtOC42NDhoMS4zMzJsLjIzMS43NzloLjA3NmMuMzgzLS41OTQuOTUtLjg5MiAxLjcwMi0uODkyLjcxIDAgMS4yNjQuMjc0IDEuNjY1LjgyMi40MDEuNTQ4LjYwMiAxLjMwOS42MDIgMi4yODMgMCAuNjQtLjA5NCAxLjE5OC0uMjgyIDEuNjctLjE4OC40NzMtLjQ1Ni44MzMtLjgwMyAxLjA4LS4zNDcuMjQ3LS43NTYuMzctMS4yMjUuMzd6TTMuOCAxOS4xOTNjLS40MDUgMC0uNy4xMjQtLjg4Ni4zNzMtLjE4Ny4yNDktLjI4My42Ni0uMjkgMS4yMzN2LjE3N2MwIC42NDUuMDk1IDEuMTA3LjI4NyAxLjM4Ni4xOTIuMjguNDk1LjQxOS45MS40MTkuNzM0IDAgMS4xMDEtLjYwNSAxLjEwMS0xLjgxNiAwLS41OS0uMDktMS4wMzQtLjI3LTEuMzI5LS4xODItLjI5NS0uNDY1LS40NDMtLjg1Mi0uNDQzem01LjU3IDEuNzk0YzAgLjU5NC4wOTggMS4wNDQuMjkzIDEuMzQ4LjE5Ni4zMDQuNTEzLjQ1Ny45NTQuNDU3LjQzNyAwIC43NS0uMTUyLjk0Mi0uNDU0LjE5Mi0uMzAzLjI4OC0uNzUzLjI4OC0xLjM1MSAwLS41OTUtLjA5Ny0xLjA0LS4yOS0xLjMzOC0uMTk0LS4yOTctLjUxLS40NDUtLjk1LS40NDUtLjQzOCAwLS43NTMuMTQ3LS45NDYuNDQzLS4xOTQuMjk1LS4yOS43NDItLjI5IDEuMzR6bTQuMTUzIDBjMCAuOTc3LS4yNTggMS43NDItLjc3NCAyLjI5My0uNTE1LjU1Mi0xLjIzMy44MjctMi4xNTQuODI3LS41NzYgMC0xLjA4NS0uMTI2LTEuNTI1LS4zNzhhMi41MiAyLjUyIDAgMCAxLTEuMDE1LTEuMDg4Yy0uMjM3LS40NzMtLjM1NS0xLjAyNC0uMzU1LTEuNjU0IDAtLjk4MS4yNTYtMS43NDQuNzY4LTIuMjg4LjUxMi0uNTQ1IDEuMjMyLS44MTcgMi4xNi0uODE3LjU3NiAwIDEuMDg1LjEyNiAxLjUyNS4zNzYuNDQuMjUxLjc3OS42MSAxLjAxNSAxLjA4LjIzNi40NjkuMzU1IDEuMDE5LjM1NSAxLjY0OXpNMTkuNzEgMjRsLS40NjItMi4xLS42MjMtMi42NTNoLS4wMzdMMTcuNDkzIDI0SDE1LjczbC0xLjcwOC02LjAwNWgxLjYzM2wuNjkzIDIuNjU5Yy4xMS40NzYuMjI0IDEuMTMzLjMzOCAxLjk3MWguMDMyYy4wMTUtLjI3Mi4wNzctLjcwNC4xODgtMS4yOTRsLjA4Ni0uNDU3Ljc0Mi0yLjg3OWgxLjgwNGwuNzA0IDIuODc5Yy4wMTQuMDc5LjAzNy4xOTUuMDY3LjM1YTIwLjk5OCAyMC45OTggMCAwIDEgLjE2NyAxLjAwMmMuMDIzLjE2NS4wMzYuMjk5LjA0LjM5OWguMDMyYy4wMzItLjI1OC4wOS0uNjExLjE3Mi0xLjA2LjA4Mi0uNDUuMTQxLS43NTQuMTc3LS45MTFsLjcyLTIuNjU5aDEuNjA2TDIxLjQ5NCAyNGgtMS43ODN6bTcuMDg2LTQuOTUyYy0uMzQ4IDAtLjYyLjExLS44MTcuMzMtLjE5Ny4yMi0uMzEuNTMzLS4zMzguOTM3aDIuMjk5Yy0uMDA4LS40MDQtLjExMy0uNzE3LS4zMTctLjkzNy0uMjA0LS4yMi0uNDgtLjMzLS44MjctLjMzem0uMjMgNS4wNmMtLjk2NiAwLTEuNzIyLS4yNjctMi4yNjYtLjgtLjU0NC0uNTM0LS44MTYtMS4yOS0uODE2LTIuMjY3IDAtMS4wMDcuMjUxLTEuNzg1Ljc1NC0yLjMzNC41MDMtLjU1IDEuMTk5LS44MjUgMi4wODctLjgyNS44NDggMCAxLjUxLjI0MiAxLjk4Mi43MjUuNDcyLjQ4NC43MDkgMS4xNTIuNzA5IDIuMDA0di43OTVoLTMuODczYy4wMTguNDY1LjE1Ni44MjkuNDE0IDEuMDkuMjU4LjI2MS42Mi4zOTIgMS4wODUuMzkyLjM2MSAwIC43MDMtLjAzNyAxLjAyNi0uMTEzYTUuMTMzIDUuMTMzIDAgMCAwIDEuMDEtLjM2djEuMjY4Yy0uMjg3LjE0My0uNTkzLjI1LS45Mi4zMmE1Ljc5IDUuNzkgMCAwIDEtMS4xOTEuMTA0em03LjI1My02LjIyNmMuMjIyIDAgLjQwNi4wMTYuNTUzLjA0OWwtLjEyNCAxLjUzNmExLjg3NyAxLjg3NyAwIDAgMC0uNDgzLS4wNTRjLS41MjMgMC0uOTMuMTM0LTEuMjIyLjQwMy0uMjkyLjI2OC0uNDM4LjY0NC0uNDM4IDEuMTI4VjI0aC0xLjYzOHYtNi4wMDVoMS4yNGwuMjQyIDEuMDFoLjA4Yy4xODctLjMzNy40MzktLjYwOC43NTYtLjgxNGExLjg2IDEuODYgMCAwIDEgMS4wMzQtLjMwOXptNC4wMjkgMS4xNjZjLS4zNDcgMC0uNjIuMTEtLjgxNy4zMy0uMTk3LjIyLS4zMS41MzMtLjMzOC45MzdoMi4yOTljLS4wMDctLjQwNC0uMTEzLS43MTctLjMxNy0uOTM3LS4yMDQtLjIyLS40OC0uMzMtLjgyNy0uMzN6bS4yMyA1LjA2Yy0uOTY2IDAtMS43MjItLjI2Ny0yLjI2Ni0uOC0uNTQ0LS41MzQtLjgxNi0xLjI5LS44MTYtMi4yNjcgMC0xLjAwNy4yNTEtMS43ODUuNzU0LTIuMzM0LjUwNC0uNTUgMS4yLS44MjUgMi4wODctLjgyNS44NDkgMCAxLjUxLjI0MiAxLjk4Mi43MjUuNDczLjQ4NC43MDkgMS4xNTIuNzA5IDIuMDA0di43OTVoLTMuODczYy4wMTguNDY1LjE1Ni44MjkuNDE0IDEuMDkuMjU4LjI2MS42Mi4zOTIgMS4wODUuMzkyLjM2MiAwIC43MDQtLjAzNyAxLjAyNi0uMTEzYTUuMTMzIDUuMTMzIDAgMCAwIDEuMDEtLjM2djEuMjY4Yy0uMjg3LjE0My0uNTkzLjI1LS45MTkuMzJhNS43OSA1Ljc5IDAgMCAxLTEuMTkyLjEwNHptNS44MDMgMGMtLjcwNiAwLTEuMjYtLjI3NS0xLjY2My0uODIyLS40MDMtLjU0OC0uNjA0LTEuMzA3LS42MDQtMi4yNzggMC0uOTg0LjIwNS0xLjc1Mi42MTUtMi4zMDEuNDEtLjU1Ljk3NS0uODI1IDEuNjk1LS44MjUuNzU1IDAgMS4zMzIuMjk0IDEuNzI5Ljg4MWguMDU0YTYuNjk3IDYuNjk3IDAgMCAxLS4xMjQtMS4xOTh2LTEuOTIyaDEuNjQ0VjI0SDQ2LjQzbC0uMzE3LS43NzloLS4wN2MtLjM3Mi41OTEtLjk0Ljg4Ni0xLjcwMi44ODZ6bS41NzQtMS4zMDZjLjQyIDAgLjcyNi0uMTIxLjkyMS0uMzY1LjE5Ni0uMjQzLjMwMi0uNjU3LjMyLTEuMjR2LS4xNzhjMC0uNjQ0LS4xLTEuMTA2LS4yOTgtMS4zODYtLjE5OS0uMjc5LS41MjItLjQxOS0uOTctLjQxOWEuOTYyLjk2MiAwIDAgMC0uODUuNDY1Yy0uMjAzLjMxLS4zMDQuNzYtLjMwNCAxLjM1IDAgLjU5Mi4xMDIgMS4wMzUuMzA2IDEuMzMuMjA0LjI5Ni40OTYuNDQzLjg3NS40NDN6bTEwLjkyMi00LjkyYy43MDkgMCAxLjI2NC4yNzcgMS42NjUuODMuNC41NTMuNjAxIDEuMzEyLjYwMSAyLjI3NSAwIC45OTItLjIwNiAxLjc2LS42MiAyLjMwNC0uNDE0LjU0NC0uOTc3LjgxNi0xLjY5LjgxNi0uNzA1IDAtMS4yNTgtLjI1Ni0xLjY1OS0uNzY4aC0uMTEzbC0uMjc0LjY2MWgtMS4yNTF2LTguMzU3aDEuNjM4djEuOTQ0YzAgLjI0Ny0uMDIxLjY0My0uMDY0IDEuMTg3aC4wNjRjLjM4My0uNTk0Ljk1LS44OTIgMS43MDMtLjg5MnptLS41MjcgMS4zMWMtLjQwNCAwLS43LjEyNS0uODg2LjM3NC0uMTg2LjI0OS0uMjgzLjY2LS4yOSAxLjIzM3YuMTc3YzAgLjY0NS4wOTYgMS4xMDcuMjg3IDEuMzg2LjE5Mi4yOC40OTUuNDE5LjkxLjQxOS4zMzcgMCAuNjA1LS4xNTUuODA0LS40NjUuMTk5LS4zMS4yOTgtLjc2LjI5OC0xLjM1IDAtLjU5MS0uMS0xLjAzNS0uMy0xLjMzYS45NDMuOTQzIDAgMCAwLS44MjMtLjQ0M3ptMy4xODYtMS4xOTdoMS43OTRsMS4xMzQgMy4zNzljLjA5Ni4yOTMuMTYzLjY0LjE5OCAxLjA0MmguMDMzYy4wMzktLjM3LjExNi0uNzE3LjIzLTEuMDQybDEuMTEyLTMuMzc5aDEuNzU3bC0yLjU0IDYuNzczYy0uMjM0LjYyNy0uNTY2IDEuMDk2LS45OTcgMS40MDctLjQzMi4zMTItLjkzNi40NjgtMS41MTIuNDY4LS4yODMgMC0uNTYtLjAzLS44MzMtLjA5MnYtMS4zYTIuOCAyLjggMCAwIDAgLjY0NS4wN2MuMjkgMCAuNTQzLS4wODguNzYtLjI2Ni4yMTctLjE3Ny4zODYtLjQ0NC41MDgtLjgwM2wuMDk2LS4yOTUtMi4zODUtNS45NjJ6Ii8+CiAgICAgICAgPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoNzMpIj4KICAgICAgICAgICAgPGNpcmNsZSBjeD0iMTkiIGN5PSIxOSIgcj0iMTkiIGZpbGw9IiNFMEUwRTAiLz4KICAgICAgICAgICAgPHBhdGggZmlsbD0iI0ZGRiIgZD0iTTIyLjQ3NCAxNS40NDNoNS4xNjJMMTIuNDM2IDMwLjRWMTAuMzYzaDE1LjJsLTUuMTYyIDUuMDh6Ii8+CiAgICAgICAgPC9nPgogICAgICAgIDxwYXRoIGZpbGw9IiNEMkQyRDIiIGQ9Ik0xMjEuNTQ0IDE0LjU2di0xLjcyOGg4LjI3MnYxLjcyOGgtMy4wMjRWMjRoLTIuMjR2LTkuNDRoLTMuMDA4em0xMy43NDQgOS41NjhjLTEuMjkgMC0yLjM0MS0uNDE5LTMuMTUyLTEuMjU2LS44MS0uODM3LTEuMjE2LTEuOTQ0LTEuMjE2LTMuMzJzLjQwOC0yLjQ3NyAxLjIyNC0zLjMwNGMuODE2LS44MjcgMS44NzItMS4yNCAzLjE2OC0xLjI0czIuMzYuNDAzIDMuMTkyIDEuMjA4Yy44MzIuODA1IDEuMjQ4IDEuODggMS4yNDggMy4yMjQgMCAuMzEtLjAyMS41OTctLjA2NC44NjRoLTYuNDY0Yy4wNTMuNTc2LjI2NyAxLjA0LjY0IDEuMzkyLjM3My4zNTIuODQ4LjUyOCAxLjQyNC41MjguNzc5IDAgMS4zNTUtLjMyIDEuNzI4LS45NmgyLjQzMmEzLjg5MSAzLjg5MSAwIDAgMS0xLjQ4OCAyLjA2NGMtLjczNi41MzMtMS42MjcuOC0yLjY3Mi44em0xLjQ4LTYuNjg4Yy0uNC0uMzUyLS44ODMtLjUyOC0xLjQ0OC0uNTI4cy0xLjAzNy4xNzYtMS40MTYuNTI4Yy0uMzc5LjM1Mi0uNjA1LjgyMS0uNjggMS40MDhoNC4xOTJjLS4wMzItLjU4Ny0uMjQ4LTEuMDU2LS42NDgtMS40MDh6bTcuMDE2LTIuMzA0djEuNTY4Yy41OTctMS4xMyAxLjQ2MS0xLjY5NiAyLjU5Mi0xLjY5NnYyLjMwNGgtLjU2Yy0uNjcyIDAtMS4xNzkuMTY4LTEuNTIuNTA0LS4zNDEuMzM2LS41MTIuOTE1LS41MTIgMS43MzZWMjRoLTIuMjU2di04Ljg2NGgyLjI1NnptNi40NDggMHYxLjMyOGMuNTY1LS45NyAxLjQ4My0xLjQ1NiAyLjc1Mi0xLjQ1Ni42NzIgMCAxLjI3Mi4xNTUgMS44LjQ2NC41MjguMzEuOTM2Ljc1MiAxLjIyNCAxLjMyOC4zMS0uNTU1LjczMy0uOTkyIDEuMjcyLTEuMzEyYTMuNDg4IDMuNDg4IDAgMCAxIDEuODE2LS40OGMxLjA1NiAwIDEuOTA3LjMzIDIuNTUyLjk5Mi42NDUuNjYxLjk2OCAxLjU5Ljk2OCAyLjc4NFYyNGgtMi4yNHYtNC44OTZjMC0uNjkzLS4xNzYtMS4yMjQtLjUyOC0xLjU5Mi0uMzUyLS4zNjgtLjgzMi0uNTUyLTEuNDQtLjU1MnMtMS4wOS4xODQtMS40NDguNTUyYy0uMzU3LjM2OC0uNTM2Ljg5OS0uNTM2IDEuNTkyVjI0aC0yLjI0di00Ljg5NmMwLS42OTMtLjE3Ni0xLjIyNC0uNTI4LTEuNTkyLS4zNTItLjM2OC0uODMyLS41NTItMS40NC0uNTUycy0xLjA5LjE4NC0xLjQ0OC41NTJjLS4zNTcuMzY4LS41MzYuODk5LS41MzYgMS41OTJWMjRoLTIuMjU2di04Ljg2NGgyLjI1NnpNMTY0LjkzNiAyNFYxMi4xNmgyLjI1NlYyNGgtMi4yNTZ6bTcuMDQtLjE2bC0zLjQ3Mi04LjcwNGgyLjUyOGwyLjI1NiA2LjMwNCAyLjM4NC02LjMwNGgyLjM1MmwtNS41MzYgMTMuMDU2aC0yLjM1MmwxLjg0LTQuMzUyeiIvPgogICAgPC9nPgo8L3N2Zz4K) center no-repeat;\"></span>\n" +
                "\n" +
                "      <div data-custom-class=\"body\">\n" +
                "      <div><div align=\"center\" class=\"MsoNormal\" data-custom-class=\"title\" style=\"text-align: left; line-height: 1.5;\"><a name=\"_4r5vko5di6yg\"></a><strong><span style=\"line-height: 150%; font-size: 26px;\">DISCLAIMER</span></strong></div><div align=\"center\" class=\"MsoNormal\" style=\"text-align:center;line-height:150%;\"><a name=\"_l2jmcqu2bv4x\"></a></div><div align=\"center\" class=\"MsoNormal\" data-custom-class=\"subtitle\" style=\"text-align: left; line-height: 150%;\"><br></div><div align=\"center\" class=\"MsoNormal\" data-custom-class=\"subtitle\" style=\"text-align: left; line-height: 150%;\"><span style=\"color: rgb(127,127,127); font-size: 15px; text-align: justify;\"><strong>Last updated </strong><bdt class=\"block-container question question-in-editor\" data-id=\"f06b270d-4b70-bc53-bef4-2d8996dff70b\" data-type=\"question\"><strong>December 03, 2024</strong></bdt></span></div><div class=\"MsoNormal\" style=\"text-align: justify; line-height: 1.5;\"><br></div><div class=\"MsoNormal\" style=\"text-align: justify; line-height: 1.5;\"><br></div><div class=\"MsoNormal\" data-custom-class=\"heading_1\"><a name=\"_xs0r05tcjblb\"></a><strong><span style=\"line-height: 115%; font-size: 19px;\">WEBSITE DISCLAIMER</span></strong></div></div><div style=\"line-height: 1.2;\"><br></div><div><div class=\"MsoNormal\" data-custom-class=\"body_text\" style=\"line-height: 1.5;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\">The information provided by <bdt class=\"block-container question question-in-editor\" data-id=\"1e91c6ac-db3b-ab40-09dc-333e7d471e6c\" data-type=\"question\">Zen Bridge</bdt> (<bdt class=\"block-component\"></bdt>\"we,\" \"us,\" or \"our\"<bdt class=\"statement-end-if-in-editor\"></bdt>) on <bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"question\"><a href=\"https://aki927.github.io/zenbridge.github.io/\" target=\"_blank\" data-custom-class=\"link\">https://aki927.github.io/zenbridge.github.io/</a></bdt> (the <bdt class=\"block-component\"></bdt>\"Site\"<bdt class=\"statement-end-if-in-editor\"></bdt>) and <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">our mobile application</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt> </bdt>is for general informational purposes only. All information on <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\">the Site and <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">our mobile application</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt> </bdt></span>is provided in good faith, however we make no representation or warranty of any kind, express or implied, regarding the accuracy, adequacy, validity, reliability, availability, or completeness of any information on <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\">the Site or <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">our mobile application</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt></bdt></span></span>. UNDER NO CIRCUMSTANCE SHALL WE HAVE ANY LIABILITY TO YOU FOR ANY LOSS OR DAMAGE OF ANY KIND INCURRED AS A RESULT OF THE USE OF <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">THE SITE OR OUR MOBILE APPLICATION</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt> </bdt></span></span>OR RELIANCE ON ANY INFORMATION PROVIDED ON <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">THE SITE AND OUR MOBILE APPLICATION</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt></bdt></span></span></span>. YOUR USE OF <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">THE SITE AND OUR MOBILE APPLICATION</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt> </bdt></span></span></span>AND YOUR RELIANCE ON ANY INFORMATION ON <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">THE SITE AND OUR MOBILE APPLICATION</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt></bdt></span></span></span> IS SOLELY AT YOUR OWN RISK.</span></div></div><div style=\"line-height: 1.2;\"><br></div><div><div class=\"MsoNormal\"><a name=\"_x1u8x12nt00e\"></a></div><bdt class=\"block-container if\" data-type=\"if\" id=\"25d6783f-eaa7-3465-7bd8-31e107cc0931\"><bdt data-type=\"conditional-block\"><bdt class=\"block-component\" data-record-question-key=\"external_disclaimer_option\" data-type=\"statement\"></bdt><bdt data-type=\"body\"><div class=\"MsoNormal\" data-custom-class=\"heading_1\"><strong><span style=\"line-height: 115%; font-size: 19px;\">EXTERNAL LINKS\n" +
                "DISCLAIMER<br></span></strong></div></bdt></bdt></bdt></div><div style=\"line-height: 1.2;\"><br></div><div><bdt class=\"block-container if\" data-type=\"if\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\"><div class=\"MsoNormal\" data-custom-class=\"body_text\" style=\"line-height: 1.5;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\">The Site and <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">our mobile application</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt></bdt></span></span><span style=\"font-size: 15px;\"><span style=\"color: rgb(89, 89, 89);\"> may contain (or you may be sent through <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-component\"></bdt><span style=\"color: rgb(89, 89, 89); font-size: 15px;\">the Site or <span style=\"color: rgb(89, 89, 89); font-size: 15px;\"><bdt class=\"block-container if\" data-type=\"if\" id=\"0043ef2f-6d7b-8e27-e1f5-16cde0f30348\"><bdt data-type=\"conditional-block\"><bdt data-type=\"body\">our mobile application</bdt></bdt></bdt></span></span></bdt></bdt><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt></bdt></span></span>) links</span> </span><span style=\"color: rgb(89, 89, 89); font-size: 15px;\">to other\n" +
                "websites or content belonging to or originating from third parties or links to\n" +
                "websites and features in banners or other advertising. Such external links are\n" +
                "not investigated, monitored, or checked for accuracy, adequacy, validity, reliability,\n" +
                "availability, or completeness by us. WE DO NOT WARRANT, ENDORSE, GUARANTEE, OR\n" +
                "ASSUME RESPONSIBILITY FOR THE ACCURACY OR RELIABILITY OF ANY INFORMATION\n" +
                "OFFERED BY THIRD-PARTY WEBSITES LINKED THROUGH THE SITE OR ANY WEBSITE OR\n" +
                "FEATURE LINKED IN ANY BANNER OR OTHER ADVERTISING. WE WILL NOT BE A PARTY TO OR\n" +
                "IN ANY WAY BE RESPONSIBLE FOR MONITORING ANY TRANSACTION BETWEEN YOU AND THIRD-PARTY PROVIDERS OF PRODUCTS OR SERVICES.</span></div></bdt></bdt></bdt></div><div style=\"line-height: 1.2;\"><br></div><div><bdt class=\"block-container if\" data-type=\"if\"><bdt class=\"statement-end-if-in-editor\" data-type=\"close\"></bdt></bdt><div class=\"MsoNormal\"><a name=\"_wfmrqujylbbj\"></a></div><bdt class=\"block-container if\" data-type=\"if\" id=\"098cd9ba-027e-0afb-ec22-41e16cb68d79\"><bdt data-type=\"conditional-block\"><bdt class=\"block-component\" data-record-question-key=\"professional_disclaimer_option\" data-type=\"statement\"></bdt></bdt></div><div><bdt class=\"block-component\"></bdt></bdt><bdt data-type=\"conditional-block\" style=\"text-align: start;\"><bdt data-type=\"body\"><div class=\"MsoNormal\"><bdt class=\"block-component\"></bdt></bdt></span></bdt></bdt></bdt></bdt></bdt></span></bdt></bdt></bdt></div><style>\n" +
                "      ul {\n" +
                "        list-style-type: square;\n" +
                "      }\n" +
                "      ul > li > ul {\n" +
                "        list-style-type: circle;\n" +
                "      }\n" +
                "      ul > li > ul > li > ul {\n" +
                "        list-style-type: square;\n" +
                "      }\n" +
                "      ol li {\n" +
                "        font-family: Arial ;\n" +
                "      }\n" +
                "    </style>\n" +
                "      </div>\n" +
                "      ";

        webView.setWebViewClient(new WebViewClient());
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        /*
        When user accept checkbox is clicked, user and click the proceed button and is sent to LoginActivity
        through an intent.
         */
        proceedButton.setOnClickListener(view -> {
            if (acceptCheckbox.isChecked()) {
                Intent intent = new Intent(CopyrightActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(CopyrightActivity.this, "You must accept terms and conditions in order to proceed.", Toast.LENGTH_SHORT).show();
            }
        });

        // App exits when the cancel button is clicked (the user did not agree to terms and conditions)
        cancelButton.setOnClickListener(view -> {
            finish();
            System.exit(0);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Adjusts the zoom dynamically on WebView for portrait and landscape mode
    private void adjustFontSizeBasedOnOrientation() {
        WebSettings webSettings = webView.getSettings();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            webSettings.setTextZoom(50);
        } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            webSettings.setTextZoom(100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontSizeBasedOnOrientation();
    }
}