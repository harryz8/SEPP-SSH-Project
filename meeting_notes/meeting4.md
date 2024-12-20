# Meeting 4

## What we have each done since last time

### Harry
I need to apologise as I have again changed my mind on how scraped prices are handled. However all changes are self-contained within my methods and so this will have no impact on others' issues. I have settled on having my own database independent of the other to manage them. I think this is simplest as then connections beween recipes and the minimum ingredient prices can be handled in the Java code. I have begun to work on this but it cannot be finished until the Hybernate database is linked to postgres through docker. I also must apologise for being behind in my unit testing.

### Tom
I began by sorting merge conflicts as my branch was not fully up to date when I committed my changes. After sorting this, I made good progress on the implementation of my recipe creator. I hope to complete this feature within the next day or two to ensure that it can be fully implemented into the program and so that we can begin work on our write-up.

### Phoebe
I have merged my branch with main in order to get my branch up to date. I have began using these merged files in order to make sure my previously made methods work fully with the rest of the implementation. I have also began to plan the implementation of a simulation for the camera which I aim to complete in the next few days

### Khaled 
I have fixed one of the tables/classes, that was the Recipe_Category class were a forgot to make it as a table, I did not add the @Entity and the @Table tags. I have looked at contents explaning the docker and contraisation. Harry and I worked in the meeting to set the docker file to the project where it's been modified after many strange errors we faced 



## What we will achieve in this meeting

1. Write about and review changes to the EDR
2. We **must** decide on what software engineering techniques to use, implement them and describe why we used them and how they are useful. We may decide to deligate some between us if we cannot achieve this today but it is the _number one priority_ after this meeting is over, as it is worth __40%__ of our mark!
3. We will integrate our code and produce a working command-line prototype _by the end of this meeting_. People may want to work on integrating their code properly with the Hybernate database if they haven't already.
4. We will complete testing on our issues and also write up a testing plan together. Testing will then be deligated.
5. We will write the threats plan together if we decide its necessary.
6. We will plan how to complete the report, and make a start. This is the number 2 priorty after this meeeting. (Harry will suggest to use [google docs](https://docs.google.com/document/d/1cL2LFy0RtviF78Xvktd1NU0f5wFtM9UwY2InLC10ANA/edit?usp=sharing) for better collaberative work and built in version history).

## What we achieved in this meeting

1. We all decided which sections of the software engineering techniques we will focus on.
2. We discussed and agreed on the changes to be made to the EDR.
3. We discussed and finished writing about the testing we have completed and which we will complete.

## Goals to achieve by our next meeting

1. We should all complete our sections of the project so that we can fully implement the program in the next meeting.
2. We should all ensure that our sections of the software engineering techniques and implemented and written up on.
3. We should begin writing our report on the Google Docs file.
4. Ensure all units pass. 
