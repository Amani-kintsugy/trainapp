package com.example.trainapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trainapp.data.Train
import com.example.trainapp.data.trainList
import com.example.trainapp.ui.theme.trainappTheme
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            trainappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TrainAppScreen()
                }
            }
        }
    }
}

@Composable
fun TrainAppScreen() {
    var departure by remember { mutableStateOf("") }
    var arrival by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TrainTopAppBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TrainSearchBar(
                departure = departure,
                onDepartureChange = { departure = it },
                arrival = arrival,
                onArrivalChange = { arrival = it },
                date = date,
                onDateChange = { date = it },
                time = time,
                onTimeChange = { time = it }
            )
            TrainList(
                trainList = trainList.filter {
                    (departure.isEmpty() || stringResource(it.departure).contains(departure, ignoreCase = true)) &&
                            (arrival.isEmpty() || stringResource(it.destination).contains(arrival, ignoreCase = true)) &&
                            (date.isEmpty() || stringResource(it.departureDate).contains(date, ignoreCase = true)) &&
                            (time.isEmpty() || stringResource(it.departureTime).contains(time, ignoreCase = true))
                },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun TrainSearchBar(
    departure: String,
    onDepartureChange: (String) -> Unit,
    arrival: String,
    onArrivalChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    time: String,
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        OutlinedTextField(
            value = departure,
            onValueChange = onDepartureChange,
            label = { Text("Departure") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = arrival,
            onValueChange = onArrivalChange,
            label = { Text("Arrival") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = date,
            onValueChange = onDateChange,
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = time,
            onValueChange = onTimeChange,
            label = { Text("Time (HH:MM)") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TrainList(
    trainList: List<Train>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(trainList) { train ->
            TrainItem(
                train = train,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun TrainItem(
    train: Train,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                TrainInformation(
                    scheduleID = train.scheduleID,
                    trainNumber = train.trainNumber,
                    destination = train.destination,
                    departure = train.departure,
                    departureTime = train.departureTime,
                    departureDate = train.departureDate
                )
                Spacer(Modifier.weight(1f))
                TrainItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {
                Trajectory(
                    trainTrajectory = train.imageResId, // Example drawable resource
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
private fun TrainItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.trainlogo), // Ensure this drawable exists
                    contentDescription = null
                )
                Text(
                    text = "SNCFT App",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}
@Composable
fun TrainInformation(
    @StringRes scheduleID: Int,
    @StringRes trainNumber: Int,
    @StringRes destination: Int,
    @StringRes departure: Int,
    @StringRes departureTime: Int,
    @StringRes departureDate: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        // Schedule ID and Train Number
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(scheduleID),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(trainNumber),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
        }
        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

        // Departure and Destination
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(departure),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
            )
            Text(
                text = "----->",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
            )
            Text(
                text = stringResource(destination),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
            )
        }
        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

        // Departure Time and Date
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(departureTime),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(departureDate),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Trajectory(
    @DrawableRes trainTrajectory: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(MaterialTheme.colorScheme.surface)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.about),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        Image(
            painter = painterResource(trainTrajectory),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(RoundedCornerShape(8.dp))
        )
    }
}


@Preview
@Composable
fun trainPreview() {
    trainappTheme(darkTheme = false) {
        TrainAppScreen()
    }
}

/**@Preview
@Composable
fun trainDarkThemePreview() {
trainappTheme(darkTheme = true) {
trainappList()
}
}
 */