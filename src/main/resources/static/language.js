let div = document.getElementById('language');
let options = Array.from(div.querySelectorAll('a'));

options.forEach(element => element.addEventListener('click',changeLanguage,false));

function changeLanguage(e){
    window.location.replace('?lang=' + e.target.innerText);
}
