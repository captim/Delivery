function showCityList() {
    document.getElementById("dropdownWarehouseList").classList.toggle("show");
    filterFunction();
}

function filterFunction() {
    let filter = document.getElementById("filter");
    console.log(filter);
    $.ajax({
        url:"/cities?amount=5&name=" + filter.value,
        type:"get",
        complete: [
            function (response) {
            $("#options").empty();
            let cities = $.parseJSON(response.responseText);
            for(let i = 0; i < cities.length; i++) {
                $('<option>' + cities[i].value + '</option>').attr({
                    value: cities[i].key,
                    onclick: "chooseCity(event)"
                }).appendTo("#options");
            }
        }
        ]
    })
}
function chooseCity(event) {
    document.getElementById("city_name").value = event.target.innerHTML;
    document.getElementById("city_id").value = event.target.value;
    document.getElementById("warehouse_number").disabled = false;
    document.getElementById("warehouse_search_button").disabled = false;
    showCityList();
}
function searchWarehouse() {
    let cityId = document.getElementById("city_id").value;
    let warehouseNumber = document.getElementById("warehouse_number").value;
    $.ajax({
        url:"/warehouses?city=" + cityId + "&number=" + warehouseNumber,
        type:"get",
        complete: [
            function (response) {
                $("#options").empty();
                let warehouses = $.parseJSON(response.responseText);
                if (warehouses.length === 0) {
                    document.getElementById("warehouse_address_msg").innerHTML = "Can't find warehouse #" + warehouseNumber + "!";
                } else {
                    document.getElementById("set_address_button").disabled = false;
                    document.getElementById("warehouse_address_msg").innerHTML = "Found warehouse: " + warehouses[0].value;
                    document.getElementById("warehouse_id").value = warehouses[0].key;
                }
                for(let i = 0; i < warehouses.length; i++) {
                    $('<option>' + warehouses[i].value + '</option>').attr({
                        value: warehouses[i].key,
                        onclick: "chooseCity(event)"
                    }).appendTo("#warehouse");
                }
            }
        ]
    })
}