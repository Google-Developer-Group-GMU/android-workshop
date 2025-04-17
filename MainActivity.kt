package com.example.gdgtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoList()
        }
    }
}

@Composable
fun TodoList() {
    var userInput by remember { mutableStateOf("")} // val = value doesnt change; var = variable can change
    var items by remember { mutableStateOf<List<String>>(emptyList()) } // create an empty list for us to store todo items

    fun removeItemAt(index: Int) {
        items = items.toMutableList().also { it.removeAt(index) }
    }

    Scaffold (
        bottomBar = {
            InputField(
                text = userInput,
                onTextChange = { userInput = it },
                onAdd = {
                    if (userInput.isNotBlank()) {
                        items = items + (userInput)
                        userInput = ""
                    }
                })
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ListItems(items, onRemove = { index -> removeItemAt(index) })
        }
    }
}

@Composable
fun InputField(text: String, onTextChange: (String) -> Unit, onAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text("Enter Todo Item")},
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onAdd) {
            Text("Add")
        }
    }
}

@Composable
fun ListItems(listitems: List<String>, onRemove: (Int) -> Unit) {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        reverseLayout = true
    ) {
        itemsIndexed(listitems) { index, listitem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical=4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillParentMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = listitem,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Button(onClick = { onRemove(index) }) {
                        Text("X")
                    }
                }
            }
        }
    }
}