package iteso.mx.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iteso.mx.room.adapters.AdapterName
import org.jetbrains.anko.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener, AnkoLogger {
    private lateinit var adapter: AdapterName
    private lateinit var add: Button
    private lateinit var wordDao: WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler : RecyclerView = find(R.id.recycler)
        add = find(R.id.add)
        add.setOnClickListener(this)

        recycler.layoutManager = LinearLayoutManager(this)
        wordDao = WordRoomDatabase.getDatabase(this).wordDao()

        doAsync {
            val words = wordDao.getWordsByCreation()
            debug(words)

            uiThread {
                adapter = AdapterName(words)
                recycler.adapter = adapter
            }

        }

    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.add -> {
                doAsync {
                    val random = Random.nextInt(1000)
                    wordDao.insert(Word("New Word $random"))
                    val words = wordDao.getWordsByCreation()
                    debug(words)
                    uiThread {
                        adapter.updateData(words)
                    }
                }
            }
        }
    }
}
