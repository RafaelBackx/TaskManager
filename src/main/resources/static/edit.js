let editbutton = document.querySelector('.fa-edit')
editbutton.addEventListener('click',edit,false);
let buttons = Array.from(document.querySelectorAll('.far'));

function edit(e){
    buttons.forEach(element => element.style.display = 'none');
    let savebutton = document.querySelector('.fa-save');
    savebutton.addEventListener('click',displayButtons,false);
    savebutton.style.display = 'inline-block';
    titleEdit(e);
    deadlineEdit(e);
    descriptionEdit(e);
}

function titleEdit(e){
    console.log(e);
    let title = document.querySelector('h1');
    title.style.display = 'None';
    let input = document.createElement('input');
    input.value = title.innerText;
    input.focus();
    input.classList = title.classList;
    document.body.insertBefore(input,title);
    input.addEventListener('keyup',function(e){
        if (e.key === "Enter" && input.value !== ''){
            title.innerText = input.value;
            title.style.display = 'block';
            document.body.removeChild(input);
        }else if (e.key === 'Enter' && input.value === ''){
            title.style.display = 'block';
            document.body.removeChild(input);
        }
    })
}

function deadlineEdit(e){
    let deadline = document.querySelector('h5');
    deadline.style.display = 'None';
    let input = document.createElement('input');
    input.focus();
    input.type = 'datetime-local';
    input.style.display = 'block';
    input.classList = deadline.classList;
    document.body.insertBefore(input,deadline);
    input.addEventListener('keyup',function(e){
        console.log(e.target.value);
        if (e.key === "Enter" && input.value !== ''){
            let date = input.value.split('T');
            deadline.innerText = `due at  ${date[0]}  ${date[1]}`;
            deadline.style.display = 'block';
            document.body.removeChild(input);
        }else if (e.key === "Enter" && input.value === ""){
            deadline.style.display = 'block';
            document.body.removeChild(input);
        }
    })
}

function descriptionEdit(e){
    let description = document.querySelector('p');
    description.style.display = 'None';
    let input = document.createElement('textarea');
    input.style.width = '50%';
    input.style.height = 'auto';
    input.innerText = description.innerText;
    input.focus();
    input.style.display = 'block';
    input.classList = description.classList;
    document.body.insertBefore(input,description);
    input.addEventListener('keyup',function(e){
        if (e.key === "Enter" && input.value !== ''){
            console.log(e.target.innerText);
            description.innerText = input.value;
            description.style.display = 'block';
            document.body.removeChild(input);
        }else if (e.key === "Enter" && input.innerText === ""){
            description.style.display = 'block';
            document.body.removeChild(input);
        }
    })
}

function displayButtons(e){
    console.log(buttons);
    buttons.forEach(element => element.style.display = 'inline-block');
    console.log(e.style.display);
    e.target.style.display = 'none';
    console.log(e.style.display);
}