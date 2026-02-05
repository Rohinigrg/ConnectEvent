package com.example.connectevent.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.R
import com.example.connectevent.model.EventUi
import com.example.connectevent.ui.theme.LightBlue
import com.example.connectevent.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.foundation.lazy.items



class DashboardPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardScreen()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(){
    val context = LocalContext.current

    data class NavItem(val label: String,val icon:Int)


    val listItems = listOf(
        NavItem(label = "Home", R.drawable.baseline_home_24),
        NavItem(label = "Events", R.drawable.baseline_event_24),
        NavItem(label = "Profile", R.drawable.outline_account_circle_24),
    )
    var selectedIndex by remember { mutableIntStateOf(0) }


    Scaffold (
        topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = LightBlue,
                        titleContentColor = White
                    ),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "ConnectEvent",
                                fontWeight = FontWeight.Bold,
                                color = White,

                            )
                            Icon(
                                painter = painterResource(R.drawable.baseline_notifications_24),
                                contentDescription =null,
                                tint = Color.White,
                                        modifier = Modifier.clickable {
                                    context.startActivity(Intent(context, Notification::class.java))
                                }
                         )
                    }}


                )
        },
                bottomBar = {
                    NavigationBar {
                        listItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedIndex == index,
                                onClick = {
                                    selectedIndex = index
//                                    if (item.label == "Profile") {
//                                        context.startActivity(Intent(context, ProfilePage::class.java))
//                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(item.icon),
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label) }
                            )
                        }
                    }

                }
    ){paddingValues ->
        when (selectedIndex) {
            0 -> HomeScreen(paddingValues)
            1 -> EventsScreen(paddingValues)
            2 -> ProfileScreen(paddingValues)
        }
    }
}
@Composable
fun HomeScreen(paddingValues: PaddingValues){
    val context = LocalContext.current

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp))
    {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Welcome, Rohini👋",
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Manage Your Community Events!",
            fontSize = 18.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            DashboardCard(
                R.drawable.joinevents,
                modifier = Modifier.weight(0.1f),
                onClick = {
                    context.startActivity(
                        Intent(context, JoinEventPage::class.java)
                    )
                })
            DashboardCard(
                R.drawable.viewevents,
                modifier = Modifier.weight(0.1f),
                onClick = {
                    context.startActivity(
                        Intent(context, ViewEventPage::class.java)
                    )
                })
        }

        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            image = R.drawable.myevents,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            onClick = {
                context.startActivity(
                    Intent(context, MyEventsPage::class.java)
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Upcoming Events",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        UpcomingEventCard(
            image = R.drawable.cleanliness,
            title = "Cleanliness Program",
            place="Kathmandu / 15 Jan / 10:00AM"
        )
        Spacer(modifier = Modifier.height(12.dp))
        UpcomingEventCard(
            image = R.drawable.music,
            title = "Music Program",
            place = "Baneshwor / 20 Feb / 11:00AM")
    }
}
@Composable
fun DashboardCard(
    image: Int,
    modifier: Modifier= Modifier,
    onClick: () -> Unit
){
    Card (
        modifier=modifier.height(130.dp)
            .clickable{ onClick()},

        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier= Modifier.fillMaxSize()
        )
    }

    }
@Composable
fun UpcomingEventCard(
    image: Int,
    title: String,
    place: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Card (modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(12.dp)){
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

            Column {
                Text(
                    text = title,
                fontSize=16.sp,
                fontWeight = FontWeight.Bold
                )
                Text(text = place,
                    fontSize = 13.sp,
                    color = Color.Gray)
            }
        }

    }

}
@Composable
fun EventsScreen(paddingValues: PaddingValues) {
    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filters = listOf("All", "Today", "Upcoming")

    val events = listOf(
        EventUi("Cleanliness Program", "Kathmandu", "15 Jan 10:00 AM"),
        EventUi("Music Program", "Baneshwor", "20 Feb 11:00 AM"),
        EventUi("Blood Donation", "Patan", "05 Mar 9:00 AM")
    )
    val displayedEvents = remember(searchText, selectedFilter) {
        events.filter { event ->

            // 🔍 Search filter
            val matchesSearch =
                event.title.contains(searchText, ignoreCase = true)

            // 🏷 Date filter
            val matchesFilter = when (selectedFilter) {
                "Today" -> event.date.contains("15 Jan") // example today
                "Upcoming" -> !event.date.contains("15 Jan")
                else -> true // "All"
            }

            matchesSearch && matchesFilter
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {

        // 🔍 Simple Search Field (cleaner than SearchBar)
        androidx.compose.material3.OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search events") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🏷 Filter Chips
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📋 Event List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(displayedEvents) { event ->
            EventCard(event) {
                    context.startActivity(
                        Intent(context, JoinEventPage::class.java)
                    )
                }
            }
        }
    }
}
@Composable
fun EventCard(event: EventUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = event.location,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = event.date,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}



@Composable
fun ProfileScreen(paddingValues: PaddingValues) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF4A90E2), CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Name
        Text(
            text = "Rohini Gurung",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        // Email
        Text(
            text = "rohini@gmail.com",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Profile Button
        Button(
            onClick = {
                // TODO: Edit profile later
            },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Edit Profile")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info cards
        ProfileInfoCard("Gender", "Female")
        ProfileInfoCard("Date of Birth", "2002-01-01")
        ProfileInfoCard("Location", "Kathmandu")

        Spacer(modifier = Modifier.height(30.dp))

        // Logout Button
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(context, LoginPage::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

@Composable
fun ProfileInfoCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(value, color = Color.Gray)
        }
    }
}


@Preview
@Composable
fun DashboardPreview(){
    DashboardScreen()
}

