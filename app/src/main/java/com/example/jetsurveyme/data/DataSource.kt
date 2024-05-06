package com.example.jetsurveyme.data

import com.example.jetsurveyme.R
import com.example.jetsurveyme.model.ComicCharacter
import com.example.jetsurveyme.model.ListItem
import com.example.jetsurveyme.model.Question

class DataSource (){
    fun getQuestion1():Question<List<ListItem>>{
        return _question1
    }

    fun getQuestion2():Question<List<ComicCharacter>>{
        return _question2
    }
}

private val _question1 = Question<List<ListItem>>(R.string.question1,
    listOf(ListItem(id=1, label = "Read"),
        ListItem(id=2, label = "Work out"),
        ListItem(id=3, label = "Draw"),
        ListItem(id=4, label = "Play video games"),
        ListItem(id=5, label = "Dance"),
        ListItem(id=6, label = "Watch movies"),
        ListItem(id=7, label = "Hiking"),
        ListItem(id=8, label = "Running"),
        ListItem(id=9, label = "Eating"))
)

private val _question2 = Question<List<ComicCharacter>>(R.string.question2,
    listOf(
        ComicCharacter(R.drawable.spark,R.string.comic1),
        ComicCharacter(R.drawable.lenz,R.string.comic2),
        ComicCharacter(R.drawable.bug_of_chaos,R.string.comic3),
        ComicCharacter(R.drawable.frag,R.string.comic4))
)

val _questionLists = listOf(
    Question<List<String>>(R.string.question1,
        listOf("Read","Work out","Draw","Play video games","Dance","Watch movies","Hiking","Running","Eating")
    ),
    Question<List<ComicCharacter>>(R.string.question2,
        listOf(
            ComicCharacter(R.drawable.spark,R.string.comic1),
            ComicCharacter(R.drawable.lenz,R.string.comic2),
            ComicCharacter(R.drawable.bug_of_chaos,R.string.comic3),
            ComicCharacter(R.drawable.frag,R.string.comic4))
    ),

)

