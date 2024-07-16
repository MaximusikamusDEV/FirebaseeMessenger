package com.example.labuniversity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.labuniversity.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var configAppBar: AppBarConfiguration
    lateinit var navControl: NavController
    private lateinit var auth: FirebaseAuth
    private var data: String? = null
    private var manager = supportFragmentManager
    private lateinit var contactsFragment: Fragment
    private lateinit var messagesFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        auth = Firebase.auth

        if(FirebaseAuth.getInstance().currentUser == null)
        {
            startActivity(Intent(this, LoginActivity::class.java))
        }

       // supportFragmentManager.beginTransaction().replace(R.id.ContViewForMenu, contactsFragment.)

        setContentView(binding.root)
        setSupportActionBar(binding.includeActBarForMain.toolbar)
        connectToController()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sign_out){
        Firebase.auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        }

       if(item.itemId == R.id.returnContacts){
           //supportFragmentManager.beginTransaction().replace(R.id.ContViewForMenu, ContactsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun connectToController() {
        this.navControl = findNavController(R.id.ContViewForMenu)

        this.configAppBar = AppBarConfiguration(
            setOf(
                R.id.ContactItem

            ), binding.mainDrawer
        )

        setupActionBarWithNavController(navControl, configAppBar)
        binding.navViewMain.setupWithNavController(navControl)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navControl.navigateUp(configAppBar) || super.onSupportNavigateUp()
    }

    fun getData(): String?{
        return this.data
    }

    fun setData(data: String){
        this.data = data

        val intent = Intent(this, MessagesActivity::class.java)
        startActivity(intent)


            val intentName = Intent(this, MessagesActivity::class.java).apply {
                putExtra("Name", data)
            }

            startActivity(intentName)
    }
}