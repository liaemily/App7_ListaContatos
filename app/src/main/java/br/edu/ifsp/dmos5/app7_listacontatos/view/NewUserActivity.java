package br.edu.ifsp.dmos5.app7_listacontatos.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.dmos5.app7_listacontatos.R;
import br.edu.ifsp.dmos5.app7_listacontatos.dao.UserDao;
import br.edu.ifsp.dmos5.app7_listacontatos.dao.UserDaoImpl;
import br.edu.ifsp.dmos5.app7_listacontatos.model.User;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findById();

        saveButton.setOnClickListener(this);
    }

    private void findById() {
        usernameEditText = findViewById(R.id.edittext_username_new_user);
        passwordEditText = findViewById(R.id.edittext_password_new_user);
        confirmPasswordEditText = findViewById(R.id.edittext_conf_password_new_user);
        saveButton = findViewById(R.id.btn_save);
    }

    @Override
    public void onClick(View view) {
        if (view == saveButton) {
            processSave();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void processSave() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        UserDao dao = new UserDaoImpl();
        User user = dao.findByUsername(username);

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError(getString(R.string.invalid_user_message));
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.invalid_user_message));
        } else if (TextUtils.isEmpty(confirmPassword)) {
            passwordEditText.setError(getString(R.string.invalid_user_message));
        } else {
            if (user == null) {
                if (password.equals(confirmPassword)) {
                    User newUser = new User(null, username, password);
                    dao.addUser(newUser);
                    Toast.makeText(this, getString(R.string.save_user_message), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.error_password_message), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.registered_user_message), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
