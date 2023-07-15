$(document).ready(function() {
    $.ajax({
      url: 'http://localhost:8080/admin/itemList', // Replace 'backend-url-here' with the actual URL to your backend endpoint
      headers: { "Authorization": sessionStorage.getItem("token") },
      success: function(response) {
        addProductsToPage(response);
      },
      error: function(error) {
        console.error(error);
      }
    });

    function addProductsToPage(data) {
      var products = data;
      var tbody = $('#productTableBody');

      products.forEach(function(product) {
        var row = $('<tr>');
        row.append($('<td>').text(product.id));
        row.append($('<td>').text(product.name));
        row.append($('<td>').text(product.description));
        row.append($('<td>').text(product.quantity));
        row.append($('<td>').text(product.type));
        row.append($('<td>').text(product.price));
        tbody.append(row);
      });
    }
  });