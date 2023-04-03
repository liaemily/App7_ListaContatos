package br.edu.ifsp.dmos5.app7_listacontatos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.edu.ifsp.dmos5.app7_listacontatos.R;
import br.edu.ifsp.dmos5.app7_listacontatos.dao.UserDaoImpl;
import br.edu.ifsp.dmos5.app7_listacontatos.model.User;
import br.edu.ifsp.dmos5.app7_listacontatos.view.constant.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button newUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        findById();

        loginButton.setOnClickListener(this);
        newUserButton.setOnClickListener(this);
    }

    private void findById(){
        usernameEditText = findViewById(R.id.edittext_user);
        passwordEditText = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.btn_login);
        newUserButton = findViewById(R.id.btn_newUser);
    }

    @Override
    public void onClick(View view) {

        if (view == loginButton) {
            processLogin();
        } else if (view == newUserButton) {
            processAddUser();
        }
    }

    void processLogin(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(username)){
            usernameEditText.setError("Preencha esse campo!");
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Preencha esse campo!");
        } else {
            openContactsActivity(username,password);
        }
    }

    void processAddUser(){
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    private void openContactsActivity(String username, String password){
        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_USERNAME, username);
        bundle.putString(Constant.USER_PASSWORD, password);

        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}