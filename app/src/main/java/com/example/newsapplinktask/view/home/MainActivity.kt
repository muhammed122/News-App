package com.example.newsapplinktask.view.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.newsapplinktask.R
import com.example.newsapplinktask.databinding.ActivityMainBinding
import com.example.newsapplinktask.view.BaseActivity
import com.google.android.material.navigation.NavigationView


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavDrawer()
        initUI()
    }

    override fun getContainerId(): Int = R.id.main_container

    private fun initUI() {
        supportFragmentManager.beginTransaction()
            .replace(getContainerId(), NewsFragment())
            .commit()
    }

    private fun initNavDrawer() {
        actionBarDrawerToggle =
            ActionBarDrawerToggle(
                this,
                binding.navDrawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )

        binding.navDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setIcon(R.drawable.search)

        binding.navigationView.setNavigationItemSelectedListener(this)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu_item, menu)
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "" + item.title, Toast.LENGTH_SHORT).show()
        return true
    }
}