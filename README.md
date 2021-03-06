<h1> EPAM-L1-TDD-messenger</h1>
You can run this application as a jar file.

It provides two modes: FILE mode and CONSOLE mode.
Enter 1 for CONSOLE mode or enter 2 for FILE mode.

<h2>How CONSOLE mode works:</h2>
You directly write your template in the terminal.
A placeholder is denoted by #{something} where
something will be changed later on.

After you completely entered your template, 
press enter TWICE to move on to the next step.

Then, you will be asked to provide values
for the placeholders you wrote in your template.

Finally, You will be asked to enter addresses where 
you want to send messages to. Once you are done
entering all addresses you need to send the message to,
press enter TWICE to complete the process.

<h2>How FILE mode works:</h2>
You need to provide ABSOLUTE paths for the input as well as
for the output files.
The program reads data from the input file, processes it, and 
then writes the result to the output file.
Note that input file must follow the application constraints.

<h3>CONSOLE mode example:</h3>

![img_2.png](img_2.png)


<h3>How input file needs to be structured:</h3>

body_start<br />
line 1 with possible placeholders<br />
line 2 with possible placeholders<br />
line 3 with possible placeholders<br />
body_end<br />
values_start<br />
placeholder1:value1<br />
placeholder2:value2<br />
values_end<br />
addresses_start<br />
address1<br />
address2<br />
address3<br />
addresses_end<br />

<h2>FILE mode example:</h2>
INPUT file:

![img_4.png](img_4.png)

![img_3.png](img_3.png)

OUTPUT file:

![img_5.png](img_5.png)
