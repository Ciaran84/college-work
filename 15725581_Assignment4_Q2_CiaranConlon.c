/*
 ============================================================================
 Name        : 15725581_Assignment4_Q2_CiaranConlon.c
 Author      : Ciaran Conlon 15725581
 Version     :
 
 Much of the code was provided by the lecturer, including the haversine_m function. The purpose 
 was to learn how to handle arrays of linked lists. As such, all code for the linked lists was written 
 by me, as were the file read/write functions.
  ============================================================================
 */

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>


#define d2r (M_PI / 180.0)

// The node structure for storing paths
struct node {
	double lat;
	double lon;
	char timeString[22];
	struct node *next;
};

// The structure for storing times containing hr, min and sec fields
struct timeStr {
	int hr;
	int min;
	int sec;
};

//struct for splits data
struct split {
	double splitLat;
	double splitLon;
	double splitElev;
	char splitTimeStr[22];
	struct split *next;
};

double haversine_m(double lat1, double long1, double lat2, double long2);
int openFileAndLoadData();
char *readStringAfterToken(char *txtstr, char *tkn, char *res, int len, int steps);
double readDoubleAfterToken(char *txtstr, char *tkn, int steps);
double calculate_tot_dist(struct node *lh);
struct node* create_list(double lat, double lon, char *timeStr);
struct node* add_to_list(double lat, double lon, char *timeStr);
struct timeStr *timeStrFromString(char *tstring, struct timeStr *tstruct);
int timeDiffV2(struct timeStr *t1, struct timeStr *t2);
//extra functions to extract and format split data
void calc_split_data(struct split *lh);
struct split* create_splits(double splitLat, double splitLon, double splitElev, char *splitTimeStr);
struct split* add_to_splits(double splitLat, double splitLon, double splitElev, char *splitTimeStr);

struct split *head1 = NULL;
struct split *curr1 = NULL;
struct node *head = NULL;
struct node *curr = NULL;

char startTimeStr[25];		//Hold the start and end time strings as global vars.
char finishTimeStr[25];

char totStartTimeStr[25];
char totFinishTimeStr[25];

char q2StartTime[25];
char q2EndTime[25];


//const char *GPX_FILE_PATH = "./inputFiles/Howth-Cross.gpx"; // location of GPX file
const char *GPX_FILE_PATH = "./inputFiles/Run4.9k.gpx"; // location of GPX file
//const char *GPX_FILE_PATH = "./inputFiles/Zell75k.gpx"; // location of GPX file
int checkTime=0,checkMins,checkSecs; //time to check for splits from


int main(void) {

	openFileAndLoadData();
	printf("Please enter the time you would like to start checking for fastest 100m at:\n Mins: ");
	scanf("%d",&checkMins);
	printf("\nSeconds: ");
	scanf("%d",&checkSecs);
	checkTime=(checkMins*60)+checkSecs;
	calculate_tot_dist(head);
	calc_split_data(head1);

	return EXIT_SUCCESS;
}

// Calculate distance between two points expressed as lat and long using
// Haversine formula.
double haversine_m(double lat1, double long1, double lat2, double long2) {
	double dlong = (long2 - long1) * d2r;
	double dlat = (lat2 - lat1) * d2r;
	double a = pow(sin(dlat / 2.0), 2)
			+ cos(lat1 * d2r) * cos(lat2 * d2r) * pow(sin(dlong / 2.0), 2);
	double c = 2 * atan2(sqrt(a), sqrt(1 - a));
	double d = 6367137 * c;

	return d;
}

//Function that is called once at the start to read in the character names.
int openFileAndLoadData() {
	int lineNum = 0;
	char theLine[120];
	char endOfHdr[10] = "<trkseg>";
	char endOfData[10] = "</trkseg>";
	char *timeStrPtr;
	char tempTimeStr[30];

	FILE *fpn = fopen(GPX_FILE_PATH, "r+"); /* open for reading */
	if (fpn == NULL) { /* check does file exist etc */
		printf("Cannot open %s for reading. \n", GPX_FILE_PATH);
		printf("Set up GPX file at %s and restart. \n", GPX_FILE_PATH);
		perror("Error opening GPX file. \n");
	} else {
		// First work through the header in the GPX file.
		// Assume header finishes at "</trkseg>" stored in endOfHdr.
		while (fgets(theLine, sizeof(theLine), fpn)
				&& ((strncmp(theLine, endOfHdr,7) != 0)))
				{
					//Skip through the file until "<trkseg>" is reached (endOfHdr).
					lineNum++;
				}

		// Read the text data and store the lat, lon and time data in a linked list.
		while (fgets(theLine, sizeof(theLine), fpn)
				&& ((strncmp(theLine, endOfData,7) != 0))) //finish when "</trkseg>" reached (endOfData)
		{
			timeStrPtr = readStringAfterToken(theLine, "<time>",tempTimeStr, 20, 6);

			add_to_list(
					readDoubleAfterToken(theLine, "lat=",5),
					readDoubleAfterToken(theLine, "lon=",5),
					timeStrPtr);

			add_to_splits( //load information into both lists at once
							readDoubleAfterToken(theLine, "lat=",5),
							readDoubleAfterToken(theLine, "lon=",5),
							readDoubleAfterToken(theLine, "<ele>",5),
							timeStrPtr);

			lineNum++;
		}
	}
	fclose(fpn);
return lineNum;
}


// a function that will calculate the total length of the track.

double calculate_tot_dist(struct node *lh){
	double lat1 = 0, lon1 = 0;
	double pathLen = 0;
	struct node *ptr = lh;
	double averagePace = 0;

	struct timeStr tm1,tm2;

	strcpy(startTimeStr, lh->timeString);

    while(ptr != NULL){
    	if (lat1 == 0){
    		// First node
    		lat1 = ptr->lat;
    		lon1 = ptr->lon;
    		 ptr = ptr->next;
    	}else
    	{
    		pathLen += haversine_m(lat1, lon1, ptr->lat, ptr->lon);
    		lat1 = ptr->lat;
    		lon1 = ptr->lon;
    		ptr = ptr->next;
    	}

    }
    FILE *fp; //output results to file
    fp = fopen("output2.txt", "a+");
		fprintf(fp,"\n---Overall Statistics---\n\n");

    fprintf(fp,"Path Length: %5.0f m \n",pathLen);

    strcpy(finishTimeStr, curr->timeString);

    	tm1 = * timeStrFromString(startTimeStr, &tm1);
    	tm2 = * timeStrFromString(finishTimeStr, &tm2);

    	fprintf(fp,"Elapsed Time: %d sec", timeDiffV2(&tm1, &tm2));

    	averagePace = timeDiffV2(&tm1, &tm2)/pathLen*1000.0/60.0;
    	fprintf(fp,"\nAverage Pace: %4.2f m/km\n\n", averagePace);
			fprintf(fp,"------------------------\n\n");
			fclose(fp);

    return pathLen;
}

void calc_split_data(struct split *lh)
{
    FILE *fp;
    fp = fopen("output2.txt","a+"); //append fil with splits data for 100m splits
	fprintf(fp,"----------100m Splits Statistics----------------\n\n------------------------------------------------\n%9s |%9s |%11s | %s","Split No.","Pace m:s","Speed km/h","Elevation M\n");
	fprintf(fp,"------------------------------------------------\n");
	double latSplitStart = 0, lonSplitStart = 0; //most of these are similar to calculate_tot_dist function
	double splitLen = 0;
	double elevChange=0;
	struct split *ptr = lh;

	int splitTime = 0, splitSecs=0, splitMins=0;
	double splitSpeed=0;
	int splitnum=0;

	int fastSplit=32767; //these 3 variables for calculating fastest split
	int tempSplitNum=1;
	int fastSplitNum;

    int totTime; //these for fastest split from a point
    int totSplitNum=1;
    int num=0; //to ensure the checking time is only found once
    int q2splitTime, q2splitMins,q2splitSecs;
	struct timeStr tm1,tm2, tm3, tm4, tm5, tm6;

	strcpy(startTimeStr, lh->splitTimeStr);
	strcpy(totStartTimeStr, lh->splitTimeStr);
	double elev1 = lh->splitElev, elev2;

    while(ptr != NULL)
	{
	    if (latSplitStart == 0)
		{
			// First node
            latSplitStart = ptr->splitLat;
            lonSplitStart = ptr->splitLon;
            ptr = ptr->next;
        }
		else
        {
			splitLen += haversine_m(latSplitStart, lonSplitStart, ptr->splitLat, ptr->splitLon);

			if(splitLen >= 100.0 || (splitLen<100.0 && ptr->next==NULL)) //identify 100m splits and id last split by ptr-> being null
            {

                strcpy(finishTimeStr, ptr->splitTimeStr); //current time is end time of split
				tm1 = * timeStrFromString(startTimeStr, &tm1);
				tm2 = * timeStrFromString(finishTimeStr, &tm2);
				splitTime = timeDiffV2(&tm1, &tm2);
				splitMins = splitTime/60;
				splitSecs = splitTime%60; //get time for split
				double splitHr = splitTime/3600.00; //split pace
				if(splitTime<fastSplit) //identify fastest split time and it's number
                {
                    fastSplit=splitTime;
                    fastSplitNum= tempSplitNum;
                }
                splitSpeed = (splitLen/1000.0)/splitHr;

				elev2 = ptr->splitElev; //elevation check
				elevChange = elev2-elev1;

				fprintf(fp, "%9d | %4d:%-3d | %10.2lf | %.2lf\n",++splitnum,splitMins,splitSecs,splitSpeed,elevChange);

				splitLen=0; //reset variables for next split
				elev1 = elev2;
				strcpy(startTimeStr, finishTimeStr);
				tempSplitNum++;
            }

            strcpy(totFinishTimeStr, ptr->splitTimeStr); //current time is end time of split
            tm3 = * timeStrFromString(totStartTimeStr, &tm3);
			tm4 = * timeStrFromString(totFinishTimeStr, &tm4);
			tm5 = * timeStrFromString(q2StartTime, &tm5);
			tm6 = * timeStrFromString(q2EndTime, &tm6);
			q2splitTime=timeDiffV2(&tm5,&tm6);
			q2splitMins=q2splitTime/60;
			q2splitSecs=q2splitTime%60;
			totTime = timeDiffV2(&tm3, &tm4);
			int totMins = totTime/60;
			int totSecs = totTime%60;

			if(totTime >= checkTime); //when the checking time is reached, record the fastest time thereafter (not working fully)
            {
                if(num==0)
                {

                    fprintf(fp,"\nThe fastest split from %d:%d onwards is number %d with a time of %d:%d\n\n",checkMins,checkSecs,totSplitNum,q2splitMins,q2splitSecs);
                    num=1;
                }
            }
			totSplitNum++;

			latSplitStart=ptr->splitLat; //reset split start coords
			lonSplitStart=ptr->splitLon;
            ptr = ptr->next;
        }
    }
    fprintf(fp,"\n--------------End Splits Statistics-------------\n\nThe fastest split was number %d with a time of %d:%d\n",fastSplitNum,(fastSplit/60),(fastSplit%60));

    fclose(fp);
}

// This function will return a  substring from string txtstr.
// The string will be searched for the first occurrence of tkn
// and then a substring on length len will be returned starting steps spaces from the
// start of tkn.
// If the token is not found in the string it returns a null pointer.

char *readStringAfterToken(char *txtstr, char *tkn, char *res, int len, int steps){
	//target string, token, length of substring and steps beyond start of token
	char *tmpstr;
	char *ret =NULL;

	tmpstr = strstr(txtstr, tkn);

	if (tmpstr ) // Checking to make sure the pointer is not NULL, i.e. strstr returned something.
	 ret = strncpy(res, (tmpstr+steps), len);

	ret[len] = '\0'; //Terminate the string before returning.
	 return ret;
}


// This function will return a double from string txtstr. The string will be searched
// for the first occurrence of tkn and the reading of the double will start steps
// places after the start of tkn.
// If the token is not found in the string it returns a value of -1.
// If there is no number after the token it returns 0.

double readDoubleAfterToken(char *txtstr, char *tkn, int steps){ //target string, token and steps beyond start of token
	char *tmpstr;
	char *rem;
	double res = -1;

	tmpstr = strstr(txtstr, tkn);

	 if (tmpstr ) // Checking to make sure the pointer is not NULL, i.e. strstr returned something.
	 res = strtod((tmpstr +steps) ,&rem);

	 return res;
}

// Create the list to be used to store the data
struct node* create_list(double lat, double lon, char *timeStr) {
	char ts[35];
	strcpy(ts,timeStr);
	struct node *ptr = (struct node*) malloc(sizeof(struct node));
	if (NULL == ptr) {
		printf("\n Node creation failed \n");
		return NULL;
	}
	ptr->lat = lat;
	ptr->lon = lon;
	strcpy(ptr->timeString,timeStr);

	ptr->next = NULL;

	head = curr = ptr;
	return ptr;
}

// Add nodes to the main data list
struct node* add_to_list(double lat, double lon, char *timeStr) {
	//char ts[35];
	//	strcpy(ts,timeStr);
		//printf("\n adding node to list with time as %s\n", timeStr);

	if (NULL == head) {
		return (create_list(lat, lon, timeStr));
	}

	struct node *ptr = (struct node*) malloc(sizeof(struct node));
	if (NULL == ptr) {
		printf("\n Node creation failed \n");
		return NULL;
	}
	ptr->lat = lat;
	ptr->lon = lon;
	strcpy(ptr->timeString,timeStr);

	ptr->next = NULL;

	curr->next = ptr;
	curr = ptr;

	return ptr;
}

// A function to populate a timeStr time structure from an GPX time string.
// e.g. "2013-09-12T15:59:18Z"

struct timeStr *timeStrFromString(char *tstring, struct timeStr *tstruct){

		sscanf(tstring+11, "%d", &tstruct->hr);
		sscanf(tstring+14, "%d", &tstruct->min);
		sscanf(tstring+17, "%d", &tstruct->sec);

	return tstruct;
}

// A function to return the time difference in seconds between
// two timeStr time structures.
// It assumes the two times are from the same day.

int timeDiffV2(struct timeStr *t1, struct timeStr *t2){
	int ret = 0, s1 = 0, s2 = 0;
	s1 = t1->hr * 3600 + t1->min * 60 + t1->sec;
	s2 = t2->hr * 3600 + t2->min * 60 + t2->sec;
	ret = s2 - s1;
	return ret;
}

struct split* create_splits(double lat, double lon, double elev, char *timeStr) {
	char ts[35];
	strcpy(ts,timeStr);
	struct split *ptr = (struct split*) malloc(sizeof(struct split));
	if (NULL == ptr) {
		printf("\n Node creation failed \n");
		return NULL;
	}
	ptr->splitLat = lat;
	ptr->splitLon = lon;
	ptr->splitElev = elev;
	strcpy(ptr->splitTimeStr,timeStr);

	ptr->next = NULL;

	head1 = curr1 = ptr;
	return ptr;
}

struct split* add_to_splits(double lat, double lon, double elev, char *timeStr) {

	if (NULL == head1) {
		return (create_splits(lat, lon, elev, timeStr));
	}

	struct split *ptr = (struct split*) malloc(sizeof(struct split));
	if (NULL == ptr) {
		printf("\n Node creation failed \n");
		return NULL;
	}
	ptr->splitLat = lat;
	ptr->splitLon = lon;
	ptr->splitElev = elev;
	strcpy(ptr->splitTimeStr,timeStr);

	ptr->next = NULL;

	curr1->next = ptr;
	curr1 = ptr;

	return ptr;
}
