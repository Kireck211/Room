package iteso.mx.room

import android.content.Context
import androidx.room.*
import java.util.*

@Entity(tableName = "word_table")
class Word(
    @PrimaryKey @ColumnInfo(name = "word") val word: String,
    val created_at: String = Date().time.toString()
)

@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER BY created_at ASC")
    fun getWordsByCreation(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()
}

@Database(entities = [Word::class], version = 1, exportSchema = false)
 abstract class WordRoomDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        fun getDatabase(context: Context) : WordRoomDatabase {
            val temporalInstance = INSTANCE
            if (temporalInstance != null) {
                return temporalInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
