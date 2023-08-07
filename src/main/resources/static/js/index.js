let parts = window.location.pathname.split('/');
console.log(parts);
let select = document.getElementById("time-choice");

select.value = "/" + parts[1];

select.addEventListener("change", () => {
    window.location.href = select.value;
});

