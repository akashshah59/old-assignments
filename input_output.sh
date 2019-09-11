st_Insert()
{
	  echo "Enter Roll no "
          read rollno
      echo "Enter student name"
          read name
      echo "Enter division"
          read div 
      echo "$rollno:$name:$div">>student.txt
      echo "         Record Inserted          "
          
}	

st_Display()
{
	echo "Roll no:Division:Division"
	cat student.txt
}

st_Search()
{
	echo "Enter the roll no of the student whose record you want to serach"
	read rollno
	if grep -q $rollno "student.txt"; then
		echo "Roll no : Name : Div"
     	grep  $rollno  student.txt
	else
		echo "Record not Found"     
    
    fi
}

st_Update()
{
	echo "Enter the roll no of the student whose record you want to update"
	read rollno
	if grep -q $rollno "student.txt"; then
		echo "Enter name"
		read name
		echo "Division"
		read div
		grep -v "$rollno" student.txt | tee student.txt    #Replace
		echo "$rollno:$name:$div">>student.txt

	else
		echo "Record not Found"     
    
    fi	
}

st_Delete()
{
	echo "Enter the roll no of the student whose record you want to delete"
	read rollno
	if grep -q $rollno "student.txt"; then
		 grep -v "$rollno" student.txt > student1.txt
		 rm -i student.txt
		 mv student1.txt student.txt
	else
		echo "Record not Found"     
    fi
}
	
while [ 1 ]
do
	echo "Choose from the following"
	echo "1.Insert new record"
	echo "2.Display all the records"
	echo "3.Search a record"
	echo "4.Update a record"
	echo "5.Delete a record"
	echo "6.Exit"
	
	read ch
	
	case $ch in
		1) st_Insert
		;;
		2) st_Display
		;;
		3) st_Search
		;;
		4) st_Update
		;;
		5) st_Delete
		;;
		6) exit
		;;
		*)echo "Raul"
		;;
	esac
done 
