/*
 ============================================================================
 Name        : CAO_Calc
 Author      : Ciarán Conlon 15725581
 Version     : 1.0
 Copyright   : (\/) (°,,,°) (\/) Why not Ziodberg?
 Description : Calculates CAO points
 ============================================================================
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const char *RESULTS_FILE_PATH = "./dataFiles/Results.dat"; // locations of files
const char *RES17_FILE_PATH = "./dataFiles/Results17.dat";
const char *GP_FILE_PATH = "./dataFiles/GradePoints.dat";
const char *GP17_FILE_PATH = "./dataFiles/GradePoints17.dat";
const int NSubs = 8;
const int GPs = 22;

typedef struct {
	char subject[12];
	char level[2];
	char grade[3];
} Result;
Result res[8];		// An array of Results

typedef struct{
	char grade[3];
	char level[2];
	int points;
} GP;
GP gpoint[22]; //array of grade point data

typedef struct{ //a struct to store the output formatting
	int points;
	char grade[3];
	char level[2];
	char subject[12];
} printres;
printres pr[8];

short int oldORnew; //will enable option of formatting

void loadResTable();
void loadGpTable();
void formatPrint();

int main(void) {

	printf("Enter 1 to use old format or 2 to use new format: ");
	scanf("%d", &oldORnew);

	if(oldORnew<1 || oldORnew > 2)
	{
		printf("Incorrect entry, program ending.\n");
		return 0;
	}
	else
	{
		loadResTable();
		loadGpTable();
		formatPrint();
	}
	return EXIT_SUCCESS;
}

void loadResTable(){
	int i = 0;
	char theLine[40];

	if (oldORnew==1) //checks which format was selected and reads in the appropriate file
	{
		FILE *fp = fopen(RESULTS_FILE_PATH,  "r+" );       /* open for reading */

		if (fp == NULL ){      /* check does file exist etc */
			perror ("Error opening Students file");
			exit(0);
		}
		for(i=0; i<NSubs; i++){
			fgets(theLine, sizeof(theLine),fp);
			// Use sscanf and also atoi to get the individual data fields.
			sscanf(theLine,"%s %s %s", res[i].subject, res[i].level, res[i].grade);
		}
		fclose (fp);
	}
	else
	{
		FILE *fp = fopen(RES17_FILE_PATH,  "r+" );       /* open for reading */

		if (fp == NULL ){      /* check does file exist etc */
			perror ("Error opening Students file");
			exit(0);
		}
		for(i=0; i<NSubs; i++){
			fgets(theLine, sizeof(theLine),fp);
			// Use sscanf and also atoi to get the individual data fields.
			sscanf(theLine,"%s %s %s", res[i].subject, res[i].level, res[i].grade);
		}
		fclose (fp);
	}
}

void loadGpTable(){
	int i = 0, j = 0;
	char theLine[40];
	char pointstring[22]; //for reading the points values as a string to be converted to int later
	if (oldORnew==1) //checks which format was selected and reads in the appropriate file
	{
		FILE *fp = fopen(GP_FILE_PATH,  "r+" );       /* open for reading */

		if (fp == NULL ){      /* check does file exist etc */
			perror ("Error opening Students file");
			exit(0);
		}
		for(i=0; i<GPs; i++){
			fgets(theLine, sizeof(theLine),fp);
			// Use sscanf and also atoi to get the individual data fields.
			sscanf(theLine,"%s %s %s", gpoint[i].grade, gpoint[i].level, pointstring);

			gpoint[i].points = atoi(pointstring);
		}
		fclose (fp);
	}
	else
	{
		FILE *fp = fopen(GP17_FILE_PATH,  "r+" );       /* open for reading */

		if (fp == NULL ){      /* check does file exist etc */
			perror ("Error opening Students file");
			exit(0);
		}
		for(i=0; i<GPs; i++){
			fgets(theLine, sizeof(theLine),fp);
			// Use sscanf and also atoi to get the individual data fields.
			sscanf(theLine,"%s %s %s", gpoint[i].grade, gpoint[i].level, pointstring);

			gpoint[i].points = atoi(pointstring);
		}
		fclose (fp);
	}
}

void formatPrint()
{
	int i, j;
	int compLev=1, compGrade=2; //comparison variables for the two structs
	char maths[] = "Maths", mathsLevel[]="H"; //comparison variables for bonus higher maths points
	int mathsComp=5;

	for (i=0;i<NSubs;i++) //put values into new struct in correct format
	{
		for (j=0;j<GPs;j++)
		{
			if(strncmp(res[i].level, gpoint[j].level, compLev)==0 && strncmp(res[i].grade, gpoint[j].grade,compGrade)==0)
			{
				pr[i].points = gpoint[j].points;
				strcpy (pr[i].grade,res[i].grade);
				strcpy (pr[i].level,res[i].level);
				strcpy (pr[i].subject,res[i].subject);
			}

		}
	}
	for (i=0;i<NSubs;i++) //update for higher maths and print
	{
		if(strncmp(pr[i].subject, maths, mathsComp)==0 && strcmp(pr[i].level, mathsLevel)==0 && pr[i].points >= 45)
		{
			pr[i].points += 25;
		}
		printf("%d %s %s %s\n", pr[i].points, pr[i].grade, pr[i].level, pr[i].subject);
	}
	//only print top 6 results
	printres tr[8]; //temporary struct for variable sorting/swapping
	for(i=1;i<=NSubs;i++) //sorting loop compares points values and arranges accordingly
	{
		for(j=1;j<=NSubs;j++)
		{
			if (pr[j].points > pr[j-1].points)
			{
				tr[j].points=pr[j].points;
				pr[j].points=pr[j-1].points;
				pr[j-1].points=tr[j].points;

				strcpy (tr[j].grade,pr[j].grade);
				strcpy (pr[j].grade,pr[j-1].grade);
				strcpy (pr[j-1].grade,tr[j].grade);

				strcpy (tr[j].level,pr[j].level);
				strcpy (pr[j].level,pr[j-1].level);
				strcpy (pr[j-1].level,tr[j].level);

				strcpy (tr[j].subject,pr[j].subject);
				strcpy (pr[j].subject,pr[j-1].subject);
				strcpy (pr[j-1].subject,tr[j].subject);
			}
		}
	}
	int total;
	for(i=1;i<=6;i++) //will only sum the top 6 results  as formatted above
	{
	total += pr[i].points;
	}
	printf("%d Total (best six)\n", total);
}
