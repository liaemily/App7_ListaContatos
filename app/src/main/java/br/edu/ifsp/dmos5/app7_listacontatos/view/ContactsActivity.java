package br.edu.ifsp.dmos5.app7_listacontatos.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import br.edu.ifsp.dmos5.app7_listacontatos.R;
import br.edu.ifsp.dmos5.app7_listacontatos.dao.UserDao;
import br.edu.ifsp.dmos5.app7_listacontatos.dao.UserDaoImpl;
import br.edu.ifsp.dmos5.app7_listacontatos.model.Contact;
import br.edu.ifsp.dmos5.app7_listacontatos.model.User;
import br.edu.ifsp.dmos5.app7_listacontatos.view.adapter.ContactSpinnerAdapter;
import br.edu.ifsp.dmos5.app7_listacontatos.view.constant.Constant;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private User user;
    private Contact contact;
    private Spinner cSpinner;
    private Button callButton;
    private Button newContactButton;
    private TextView nameEditText;
    private TextView phoneEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findById();

        callButton.setOnClickListener(this);
        newContactButton.setOnClickListener(this);

        processLogin();
    }

    private void findById(){
       nameEditText = findViewById(R.id.textview_name);
       phoneEditText = findViewById(R.id.textview_phone);
       callButton = findViewById(R.id.btn_call);
       newContactButton = findViewById(R.id.btn_newContact);
    }

    @Override
    public void onClick(View view) {
        if (view == callButton) {
            processCall();
        } else if (view == newContactButton) {
            processAddContact();
        }
    }

    private void processCall(){

        System.out.println(contact.getPhone().toString());
        Uri uri = Uri.parse("tel:" + contact.getPhone().toString());
        Intent intent = new Intent(Intent.ACTION_CALL,uri);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactsActivity.this, new String[]
                    {Manifest.permission.CALL_PHONE}, 1);
            startActivity(intent);

        } else {
            startActivity(intent);
        }
    }

    private void processLogin() {

        getUser();

        if (user != null) {
            if (user.getPassword().equals(getUserPasswordFromBundle())) {
                cSpinner = findViewById(R.id.spinner_contacts);
                cSpinner.setOnItemSelectedListener(this);
                populateSpinner();
            } else {
                Toast.makeText(this, getString(R.string.invalid_password_message), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else {
            Toast.makeText(this, getString(R.string.invalid_user_message), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private String getUserUsernameFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userUsername = bundle.getString(Constant.USER_USERNAME, null);
            return userUsername;
        }
        return null;
    }

    private String getUserPasswordFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userPassword = bundle.getString(Constant.USER_PASSWORD, null);
            return userPassword;
        }
        return null;
    }

    private void getUser() {
        UserDao dao = new UserDaoImpl();
        user = dao.findByUsername(getUserUsernameFromBundle());
    }

    private void populateSpinner(){
        List<Contact> dataset = new UserDaoImpl().contacts(getUserUsernameFromBundle());
        dataset.add(0, null);
        ContactSpinnerAdapter adapter = new ContactSpinnerAdapter(this, android.R.layout.simple_spinner_item, dataset);
        cSpinner.setAdapter(adapter);
    }

    private void processAddContact(){
        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_USERNAME, getUserUsernameFromBundle());
        bundle.putString(Constant.USER_PASSWORD, getUserPasswordFromBundle());

        Intent intent = new Intent(this, NewContactActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        contact = (Contact) cSpinner.getItemAtPosition(position);
        if(contact != null){
            nameEditText.setText(contact.getName());
            phoneEditText.setText(contact.getPhone());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
