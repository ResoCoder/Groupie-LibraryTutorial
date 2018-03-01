package com.resocoder.groupielibrarytutorial

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val excitingSection = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val boringFancyItems = generateFancyItems(6)
        val excitingFancyItems = generateFancyItems(12)

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            spanCount = 3
        }

        recycler_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }

        ExpandableGroup(ExpandableHeaderItem("Boring Group"), true).apply {
            add(Section(boringFancyItems))
            groupAdapter.add(this)
        }

        ExpandableGroup(ExpandableHeaderItem("Exciting Group"), false).apply {
            excitingSection.addAll(excitingFancyItems)
            add(excitingSection)
            groupAdapter.add(this)
        }

        fab.setOnClickListener {
            excitingFancyItems.shuffle()
            excitingSection.update(excitingFancyItems)
        }
    }

    private fun generateFancyItems(count: Int): MutableList<FancyItem>{
        val rnd = Random()
        return MutableList(count){
            val color = Color.argb(255, rnd.nextInt(256),
                    rnd.nextInt(256), rnd.nextInt(256))
            FancyItem(color, rnd.nextInt(100))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
