$.ajax({
    url: "http://localhost:8080/products/earring",
    cors: true,
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function(response) { addProductstoPage(response) },
    error: function(error) { console.error(error) }
});




