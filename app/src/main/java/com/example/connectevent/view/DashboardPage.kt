package com.example.connectevent.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.R
import com.example.connectevent.ViewEventPage
import com.example.connectevent.ui.theme.LightBlue
import com.example.connectevent.ui.theme.White


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
                                painter = painterResource(R.drawable.outline_account_circle_24),
                                contentDescription =null,
                                tint = Color.White
                         )
                    }}


                )
        },
                bottomBar = {
            NavigationBar {
                listItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
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
            text = "Welcome 👋",
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
                R.drawable.createimg,
                onClick = {
                    context.startActivity(
                        Intent(context, CreateEventPage::class.java)
                    )
                })
            DashboardCard(
                R.drawable.viewevents,
                onClick = {
                    context.startActivity(
                        Intent(context, ViewEventPage::class.java)
                    )
                })
        }

        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            image = R.drawable.myevents,
            modifier = Modifier.align(Alignment.CenterHorizontally),
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
            place="Kathmandu"
        )
        Spacer(modifier = Modifier.height(12.dp))
        UpcomingEventCard(
            image = R.drawable.music,
            title = "Music Program",
            place = "Baneshwor")
    }
}
@Composable
fun DashboardCard(
    image: Int,
    modifier: Modifier= Modifier,
    onClick: () -> Unit
){
    Card (
        modifier=modifier.width(150.dp).height(130.dp)
            .clickable{ onClick()},

        shape = RoundedCornerShape(8.dp),
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
fun EventsScreen(paddingValues: PaddingValues){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ){
        Text("Events Screen")
    }}
@Composable
fun ProfileScreen(paddingValues: PaddingValues){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ){
        Text("Profile Screen")
    }}



@Preview
@Composable
fun DashboardPreview(){
    DashboardScreen()
}

