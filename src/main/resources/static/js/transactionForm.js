let checkbox = document.getElementById("recurring");
let category = document.getElementById("category");
let frequency = document.getElementById("frequency");
let nextCharge = document.getElementById("next-charge");

let freq = document.getElementById("freq");
let cat = document.getElementById("cat");
let next = document.getElementById("next")

checkbox.addEventListener("click", () => {
    if (checkbox.checked) {
        category.classList.add("d-block");
        frequency.classList.add("d-block");
        nextCharge.classList.add("d-block")
        category.classList.remove("d-none");
        frequency.classList.remove("d-none");
        nextCharge.classList.remove("d-none");
    }
    else {
        category.classList.add("d-none");
        frequency.classList.add("d-none");
        nextCharge.classList.add("d-none");
        category.classList.remove("d-block");
        frequency.classList.remove("d-block");
        nextCharge.classList.remove("d-block");
        cat.value = "NA"
        freq.value = "NA";

    }
})