//import androidx.annotation.NonNull;logIn.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        String email = firebaseEmail.getText().toString().trim();
//        String password = firebasePass.getText().toString().trim();
//
//        if (TextUtils.isEmpty(email)){
//        firebaseEmail.setError("Email Field is Empty");
//        return;
//        }
//        if (TextUtils.isEmpty(password)){
//        firebasePass.setError("Password Field is Empty");
//        return;
//        }
//        if (password.length() < 8){
//        firebaseEmail.setError("Please enter 8 Characters or more");
//        return;
//        }
//
//
//
//        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//@Override
//public void onComplete(@NonNull Task<AuthResult> task) {
//        if (task.isSuccessful()){
//        Toast.makeText(LoginActivity.this,"Successful LogIn",Toast.LENGTH_LONG).show();
//        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//        }else {
//        Toast.makeText(LoginActivity.this,"ERROR"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
//
//        }
//        }
//        });
//        }
//        });
//        signUp.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
//        }
//        });
//
//        forgetPass.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//final EditText resetMail = new EditText(v.getContext());
//        AlertDialog.Builder passReset = new AlertDialog.Builder(v.getContext());
//        passReset.setTitle("Reset Password !!");
//        passReset.setMessage("Enter your Email to Recieve the Reset Link");
//        passReset.setView(resetMail);
//
//        passReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface dialog, int which) {
//
//        String mail = resetMail.getText().toString();
//        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void aVoid) {
//        Toast.makeText(LoginActivity.this,"Reset Email was Sent",Toast.LENGTH_LONG).show();
//        }
//        }).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(LoginActivity.this,"ERROR !!"+ e.getMessage(),Toast.LENGTH_LONG).show();
//        }
//        });
//
//        }
//        });
//        passReset.setPositiveButton("No", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface dialog, int which) {
//
//
//        }
//        });
//        passReset.create().show();
//









//public void remove(int adapterPosition) {
//    } adapter




//onSwipDelete

//import androidx.annotation.NonNull;new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//@Override
//public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        return false;
//        }
//
//@Override
//public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//        favoritesAdapter.remove(viewHolder.getAdapterPosition());
//        favoritesAdapter.notifyDataSetChanged();
//        Toast.makeText(FavoritesActivity.this,"Meal Deleted",Toast.LENGTH_LONG).show();
//
//        }
//        }).attachToRecyclerView(favoritesRecyclerview);
//        }