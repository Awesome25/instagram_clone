/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  Boolean signUpModeActive = true;
  TextView changeSignupModeTextView;
  EditText passwordEditText;


  public void showUserList(){

    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
    startActivity(intent);
    finish();

  }

  @Override
  public boolean onKey(View view, int keyCode, KeyEvent event) {

    if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

      signUp(view);

    }

    return false;
  }

  @Override
  public void onClick(View view) {            // whenever there is a tap on the screen

    if(view.getId() == R.id.changeSignupModeTextView){
      //Log.i("App Info", "Change SignUp Mode!");

      Button signupButton = (Button) findViewById(R.id.signupButton);

      if(signUpModeActive){

        signUpModeActive = false;
        signupButton.setText("Login");
        changeSignupModeTextView.setText("Or, Sign Up");

      }

      else{

        signUpModeActive = true;
        signupButton.setText("Sign Up");
        changeSignupModeTextView.setText("Or, Login");

      }

    }
    else if(view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView){

      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

  }

  public void signUp(View view){

    EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
    //passwordEditText = (EditText) findViewById(R.id.passwordEditText);

    if(usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){
      Toast.makeText(this, "A username and password are required!", Toast.LENGTH_SHORT).show();
    }

    else{

      if(signUpModeActive) {

        ParseUser user = new ParseUser();

        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {

              Log.i("SignUp", "Successful!");

              showUserList();

            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
      else {

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if(user != null){

              Log.i("SignUp", "Login Successful");

              showUserList();

            }
            else{

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

          }
        });

      }
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Instagram");

    //creating classes and objects
    /*
    ParseObject score = new ParseObject("Score");
    score.put("username", "anuj");
    score.put("score", 86);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        if(e == null){
          Log.i("SaveInBackground", "Successful.");
        }

        else{
          Log.i("SaveInBackground", "Failed. Error: " + e.toString());
        }

      }
    });
    */



    //Restoring and updating objects
    /*
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("UWh5ivRIRi", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {

        if(e == null && object != null){

          object.put("score", 250);
          object.saveInBackground();

          Log.i("ObjectValue", object.getString("username"));
          Log.i("ObjectValue", Integer.toString(object.getInt("score")));
        }

        else{

        }

      }
    });
    */



    //save all parse objects and display them in app

    /*
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");


    query.whereEqualTo("username", "sachin");  // search for a particular user
    query.setLimit(1);                         // set limit for only one object


    //query.whereGreaterThan("score", "250");    // objects which have score value > 250


    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e == null){
          Log.i("findInBackground", "Retrieved " + objects.size() + " objects");
          if(objects.size()>0){
            for(ParseObject object : objects){
              Log.i("findInBackgroundResult", Integer.toString(object.getInt("score")));
            }
          }
        }
      }
    });
    */



    //Sign Up Code
    /*
    ParseUser user = new ParseUser();

    user.setUsername("thebugdroid");
    user.setPassword("anujbhasin");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {

        if(e == null){

          Log.i("Sign Up", "Successful!");

        }

        else{

          Log.i("Sign Up", "Failed!");

        }

      }
    });
    */



    // Sign In Users

    /*
    ParseUser.logInInBackground("thebugdroid", "anuj", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {

        if(user != null){

          Log.i("Login", "Successful!");

        }

        else{

          Log.i("Login", "Failed! " + e.toString());

        }

      }
    });
    */



    // Test to see if user is logged in
    /*
    if(ParseUser.getCurrentUser() != null){
      Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());
    }

    else{
      Log.i("currentUser", "User not logged in");
    }
    */


    // Logout user
    /*
    ParseUser.logOut();  // logs out the user
    if(ParseUser.getCurrentUser() != null){
      Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());
    }

    else{
      Log.i("currentUser", "User not logged in");
    }
    */


    changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);
    changeSignupModeTextView.setOnClickListener(this);

    RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);
    ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView);

    backgroundRelativeLayout.setOnClickListener(this);
    logoImageView.setOnClickListener(this);

    passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    passwordEditText.setOnKeyListener(this);

    if(ParseUser.getCurrentUser() != null){

      showUserList();

    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


}