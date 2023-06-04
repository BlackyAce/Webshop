document.getElementById("createProduct-form").addEventListener("submit", function(event){
    event.preventDefault();

    const formData = new FormData();
    formData.append("inputProductTitel", document.querySelector("#inputProductTitel").value);
    formData.append("inputProductDescription", document.querySelector("#inputProductDescription").value);
    formData.append("inputProductPrice", document.querySelector("#inputProductPrice").value);
    formData.append("inputProductQuantity", document.querySelector("#inputProductQuantity").value);
    formData.append("inputProductType", document.querySelector("#inputProductType").value);
    formData.append("inputProductFile", document.querySelector("#inputProductFile").files[0]);
    

    $.ajax ( {
        url: 'http://localhost:8080/products',
        method: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify ({
            name: document.querySelector("#inputProductTitel").value,
            description: document.querySelector("#inputProductDescription").value,
            price: document.querySelector("#inputProductPrice").value,
            quantity: document.querySelector("#inputProductQuantity").value,
            type: document.querySelector("#inputProductType").value,
            imageUrl: document.querySelector("#inputProductFile").value,
            
        }), 
        success: function (response){console.log("Product added")},
        error: function (error){console.log(error)}
    });
});

