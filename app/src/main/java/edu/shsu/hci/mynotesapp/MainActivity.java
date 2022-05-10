package edu.shsu.hci.mynotesapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.telephony.SmsManager;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.shsu.hci.mynotesapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onSendEmail (View view){
        Intent emailIntent = new Intent (Intent.ACTION_SEND);
        EditText recepient = (EditText)findViewById(R.id.editTextTextEmailAddress);
        String recep_addr = recepient.getText().toString();
        EditText getCC = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        String CC_addr = getCC.getText().toString();
        EditText getBCC = (EditText) findViewById(R.id.editTextTextEmailAddress3);
        String BCC_addr = getBCC.getText().toString();
        EditText getSubject = (EditText) findViewById(R.id.editTextTextSubject);
        String emailSubject = getSubject.getText().toString();
        EditText getMessage = (EditText) findViewById(R.id.editTextTextSendMessage);
        String message = getMessage.getText().toString();

        emailIntent.putExtra(Intent.EXTRA_EMAIL, recep_addr);
        emailIntent.putExtra(Intent.EXTRA_CC, CC_addr);
        emailIntent.putExtra(Intent.EXTRA_BCC, BCC_addr);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(emailIntent);
    }

     public void onSendText (View view){
        TextView textView = (TextView) findViewById(R.id.editTextRecipient);
        String phoneNum = String.format("smsto: %s", textView.getText().toString());
        EditText mEditText = (EditText) findViewById(R.id.editTextTextSendMessage3);
        String message = mEditText.getText().toString();

         Intent textIntent = new Intent(Intent.ACTION_SENDTO);
         textIntent.setData(Uri.parse(phoneNum));
         textIntent.putExtra("sms_body", message);


         EditText destinationEditText = (EditText) findViewById(R.id.editTextRecipient);
         String destination = destinationEditText.getText().toString();
         String address = null;
         PendingIntent sent = null;
         PendingIntent delivered = null;

         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(destination,address,message,sent,delivered);

         startActivity(textIntent);
         Toast.makeText(this,"Sent!",Toast.LENGTH_LONG).show();
    }
}
