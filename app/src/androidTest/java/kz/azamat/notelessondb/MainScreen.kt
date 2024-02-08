package kz.azamat.notelessondb

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object MainScreen: KScreen<MainScreen>() {

    private val buttonAddNote = KButton{ withId(R.id.add_button) }
    
    val noteRv = KRecyclerView(
        builder = { withId(R.id.notes_rv) },
        itemTypeBuilder = { itemType(::NoteItem) }
    )

    fun clickAddNote(){
        buttonAddNote.click()
    }

    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    class NoteItem(matcher: Matcher<View>) : KRecyclerItem<NoteItem>(matcher) {

        val deleteIcon = KImageView(matcher) { withId(R.id.delete_icon) }
        val tvNoteTitle = KTextView(matcher) { withId(R.id.title_tv) }
        val tvNoteText = KTextView(matcher) { withId(R.id.notes_tv) }

    }
}