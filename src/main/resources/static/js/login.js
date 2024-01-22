//document.addEventListener('DOMContentLoaded', function() {
//    document.querySelector('form').addEventListener('submit', function(event) {
//        event.preventDefault();
//        const username = document.getElementById('username').value;
//        const password = document.getElementById('password').value;
//        const data = { username: username, password: password };
//
//        fetch('/api/v1/admin/login', {
//            method: 'POST',
//            headers: {
//                'Content-Type': 'application/json',
//            },
//            body: JSON.stringify(data),
//        })
//        .then(response => response.json())
//        .then(data => {
//            // Handle the response data, e.g., store the JWT and redirect to the dashboard
//            console.log(data)
//        })
//        .catch(error => {
//            // Handle any errors
//            console.error(error)
//        });
//    });
//});
