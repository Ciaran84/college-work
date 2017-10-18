/* Playlist sort and shuffle program
by: Ciaran Conlon 15725581 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int ROWS; //globally declared, to be defined by the number of rows read in from the text file
#define COLS 100 //sufficient for storing even a long artist/song name

const char *PLAYLIST_FILE_PATH = "./input/playlist.txt"; // location of playlist file
char playlistArr[100][COLS]; // stores playlist data (limited to 100 rows)


void sort(char playlistArr[ROWS][COLS]);

void shuff(char playlistArr[ROWS][COLS]);

int main(void)
{
	int lineNum = 0;
	int i; //loop guard


	FILE *fp = fopen(PLAYLIST_FILE_PATH,  "r+" );       /* open for reading */
	// This will take each row in the file and store it in playlistArr.
	if (fp == NULL ){      /* check does playlist file exist etc */
		perror ("Error opening playlist");
		lineNum = -1; /* use this as a file not found code */
	} else {
		// fgets returns NULL when it gets to the end of the file
		while ( fgets( playlistArr[lineNum], sizeof(playlistArr[lineNum]), fp ) != NULL ) {
			lineNum++;
		}
		fclose (fp);
	}
	ROWS=lineNum;

	// Print out the playlist
	for(i=0;i<ROWS;i++)
	{
		printf("%s",playlistArr[i]);
	}
	puts("");


	printf("Sorted list: \n");

	sort(playlistArr);

	printf("\n\nShuffled list: \n");

	shuff(playlistArr);


	return 0;
}


//***************SORT FUNCTION*************************


void sort(char list[ROWS][COLS])

{
	int i, j;

	char tmp[100];
	int comp = 100;

	for (i=1;i<=ROWS;i++)
	{
		for (j=1;j<ROWS;j++)
		{	//compare and sort by artist names
			if (strncmp(list[j], list[j-1], comp)<0)
			{
				strcpy (tmp, list[j]);
				strcpy (list[j], list[j-1]);
				strcpy (list[j-1], tmp);
			}
		}
	}

	for (i=0;i<=ROWS;i++)
	{
		printf("%s", list[i]);
	}
}

//****************SHUFFLE FUNCTION***************************

void shuff(char list[ROWS][COLS])

{
	int i, j, chk;
	char tmp[100];
	srand(time(NULL));

	do
	{
		chk=0; //(re)initialise chk to 0
		for(i=0;i<ROWS;i++) //randomize the array
		{
			j=rand()%ROWS;
			strcpy(tmp, list[j]);
			strcpy(list[j], list[i]);
			strcpy(list[i], tmp);
		}
		for(i=1;i<ROWS;i++)
		{
			if(strncmp(list[i],list[i-1],6)==0)
			{
				chk++; //increment if similarities found
			}
		}

	} while(chk>0); //loop again if similarities were found


	for(i=0;i<ROWS;i++)
	{
		printf("%s", list[i]); //print final shuffled list
	}
}
