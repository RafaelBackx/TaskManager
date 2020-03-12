let dropdowns = document.getElementsByClassName('dropdown');
let dropdownButtons = document.getElementsByClassName('dropdownButton');
let ULtasklist = document.getElementById('taskList');
let tasklist = ULtasklist.querySelectorAll('li.supperTask');
let dropdownStatus = {};
for (let i = 0;i<dropdowns.length;i++){
    dropdownStatus[i] = false;
}

for (let i =0;i<dropdownButtons.length;i++){
    dropdownButtons[i].addEventListener('click',showSubTasks,false);
}

function showSubTasks(e){
    for (let i=0;i<tasklist.length; i++){
        if (e.target.parentNode.parentNode.parentNode === tasklist[i]){
            if (dropdownStatus[i] === false){
                dropdowns[i].className = dropdowns[i].className.replace('d-none','d-block');
                dropdownStatus[i] = true;
                e.target.style.transform = 'rotate(180deg)';
            }else{
                dropdowns[i].className = dropdowns[i].className.replace('d-block','d-none');
                dropdownStatus[i] = false;
                e.target.style.transform = 'rotate(0deg)';
            }
           break;
        }
    }
}