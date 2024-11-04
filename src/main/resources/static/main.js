$(document).ready(function() {
    loadProducts();
    $("#productForm").on('submit', function(e) {
        e.preventDefault();
        saveProduct();
    });
});

function loadProducts() {
    $.ajax({
        url: '/api/products',
        type: 'GET',
        success: function(products) {
            let productList = '';
            $.each(products, function(index, product) {
                productList += `<tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.description}</td>
                    <td>
                        <button onclick="editProduct(${product.id})" class="btn btn-warning">Edit</button>
                        <button onclick="deleteProduct(${product.id})" class="btn btn-danger">Delete</button>
                    </td>
                </tr>`;
            });
            $('#productList').html(productList);
        }
    });
}

function saveProduct() {
    const productData = {
        id: $('#productId').val(),
        name: $('#name').val(),
        price: $('#price').val(),
        description: $('#description').val()
    };
    const apiUrl = productData.id ? `/api/products/${productData.id}` : '/api/products';
    const apiType = productData.id ? 'PUT' : 'POST';
    $.ajax({
        url: apiUrl,
        type: apiType,
        contentType: 'application/json',
        data: JSON.stringify(productData),
        success: function() {
            resetForm();
            loadProducts();
        }
    });
}

function editProduct(id) {
    $.ajax({
        url: `/api/products/${id}`,
        type: 'GET',
        success: function(product) {
            $('#productId').val(product.id);
            $('#name').val(product.name);
            $('#price').val(product.price);
            $('#description').val(product.description);
        }
    });
}

function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        $.ajax({
            url: `/api/products/${id}`,
            type: 'DELETE',
            success: function() {
                loadProducts();
            }
        });
    }
}

function resetForm() {
    $('#productId').val('');
    $('#name').val('');
    $('#price').val('');
    $('#description').val('');
}
