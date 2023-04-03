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
import br.edu.ifsp.dmos5.app7_listacontatos.model.Contact;
import br.edu.ifsp.dmos5.app7_listacontatos.model.User;
import br.edu.ifsp.dmos5.app7_listacontatos.view.constant.Constant;

public class NewContactActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText surnameEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findById();

        saveButton.setOnClickListener(this);
    }

    private void findById() {
        surnameEditText = findViewById(R.id.edittext_surname_new_contact);
        nameEditText = findViewById(R.id.edittext_name_new_contact);
        phoneEditText = findViewById(R.id.edittext_phone_new_contact);
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

    private String getUserUsernameFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userUsername = bundle.getString(Constant.USER_USERNAME, null);
            return userUsername;
        }
        return null;
    }
    private void processSave(){

        String surname = surnameEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        UserDao dao = new UserDaoImpl();
        User user = dao.findByUsername(getUserUsernameFromBundle());
        Contact contact = dao.findContactByUsername(user.getUsername(), surname);

        if (TextUtils.isEmpty(surname)) {
            surnameEditText.setError(getString(R.string.invalid_user_message));
        } else if (TextUtils.isEmpty(name)) {
            nameEditText.setError(getString(R.string.invalid_user_message));
        } else if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError(getString(R.string.invalid_user_message));
        } else {
            if (contact == null) {
                Contact newContact = new Contact(surname, name, phone);
                dao.addContact(user,newContact);
                Toast.makeText(this, getString(R.string.save_contact_message), Toast.LENGTH_LONG).show();
                finish();
            }
            else {
                Toast.makeText(this, getString(R.string.registered_contact_message), Toast.LENGTH_SHORT).show();
            }
        }

    }


}
