/*
  RESTFul Services by NodeJs
  Author: Fabio
  Update: 18/11/2020
 */


var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//hello

var app = express();
var multer, storage, path, crypto;
multer = require('multer')
path = require('path');
var ima = "";

// Connect to MySQL
var con = mysql.createConnection({
    host: 'localhost', // Replace your HOST IP
    user: 'root',
    password: '',
    database:'sporty'
});

// PASSWORD ULTIL
var genRandomString = function (length) {
    return crypto.randomBytes(Math.ceil(length / 2))
        .toString('hex') /* Convert to hexa format */
        .slice(0, length); /* Return required number of characters */
};

var sha512 = function (password, salt) {
    var hash = crypto.createHmac('sha512', salt); // User SHA512
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt: salt,
        passwordHash: value
    };
};

function saltHashPassword(userPassword) {
    var salt = genRandomString(16); // GenRandomString with 16 characters to salt
    var passwordData = sha512(userPassword, salt);
    return passwordData;
}

function checkHashPassword(userPassword, salt) {
    var passwordData = sha512(userPassword, salt);
    return passwordData;
}

var app = express();
app.use(bodyParser.json()); //Accept JSON Params
app.use(bodyParser.urlencoded({ extended: true })); // Accept URL Encoded params

app.post('/register/', (req, res, next) => {
    var post_data = req.body; // Get POST params
    var uid = uuid.v4(); // Get UUID  V4 like '110abacsasas-af0x-90333-casasjkajksk
    var plaint_password = post_data.password; // Get Password from POST params
    var hash_data = saltHashPassword(plaint_password);
    var password = hash_data.passwordHash; // Get hash value
    var salt = hash_data.salt; // Get salt 
    var name = post_data.name;
    var lname = post_data.lname;
    var phone = post_data.phone;
    var image = post_data.image;
    var email = post_data.email;
    con.query('SELECT * FROM user where email=?', [email], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MySQL ERROR]');
        });
        if (result && result.length)
            res.json('User already exists!!!');
        else {
            con.query('INSERT INTO `User`(`unique_id`, `name`, `lname`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`, `phone`, `image`) VALUES(?, ?, ?, ?, ?,?, NOW(), NOW(), ?, ?)',
           [uid, name, lname, email, password, salt, phone, ima], function(err, result, fields) {
                    con.on('error', function (err) {
                        console.log('[MySQL ERROR]', err);
                        res.json('Register Error: ', err);

                    });
                    res.json('Register Successful!');
        })
        }
    });
   
})


app.post('/login/', (req, res, next) => {
    var post_data = req.body;

    // Extract email and password from request
    var user_password = post_data.password;
    var email = post_data.email;
    con.query('SELECT * FROM user where email=?', [email], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MySQL ERROR]');
        });
        if (result && result.length) {
            var salt = result[0].salt; // Get salt of result if account exists
            var encrypted_password = result[0].encrypted_password;
            //Hash password from Login request with salt in Database
            var hashed_password = checkHashPassword(user_password, salt).passwordHash;
            if (encrypted_password == hashed_password)
                //res.end(JSON.stringify(result[0])) // If password is true, return all info of User
                res.json(result);
            else 
                res.end(JSON.stringify('Wrong Password'));
        }
        else {
            res.json('User Not Found!!!');
        }
    });

})


//Delete user
app.delete('/user/:id', (req, res) => {
    con.query('DELETE FROM user WHERE id = ?', [req.params.id], (err, rows, fields) => {
        if (!err)
            res.send('Deleted successfully.');
        else
            console.log(err);
    })
});

//Update user
app.put('/user/update/:id', function(req,res,next){
    console.log("RRRRRRRRR")
    var post_data = req.body;  //Get POST params
    var name = post_data.name;
    var lname = post_data.lname;
    var phone = post_data.phone;
    var email = post_data.email;
    con.query('UPDATE user SET name = ?,lname = ?,email = ?,password = ?,phone = ?,email = ? WHERE id = ?', [name,lname,phone,email,req.params.id],function (err,result,fields) {
                if (err) throw err;
                res.json('User updated');
                console.log('User updated');

            });
        })

        
var form = "<!DOCTYPE HTML><html><body>" +
    "<form method='post' action='/upload' enctype='multipart/form-data'>" +
    "<input type='file' name='upload'/>" +
    "<input type='submit' /></form>" +
    "</body></html>";

app.get('/', function (req, res) {
    res.writeHead(200, { 'Content-Type': 'text/html' });
    res.end(form);

});

// Include the node file module
var fs = require('fs');

storage = multer.diskStorage({
    destination: './uploads/',
    filename: function (req, file, cb) {
        return crypto.pseudoRandomBytes(16, function (err, raw) {
            if (err) {
                return cb(err);
            }
            return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
        });
    }
});



// Post files
app.post(
    "/upload",
    multer({
        storage: storage
    }).single('upload'), function (req, res) {
        console.log(req.file);
        console.log(req.body);
        res.redirect("/uploads/" + req.file.filename);
        console.log(req.file.filename);
        ima = req.file.filename;
        console.log(photo_evenement);
        return res.status(200).end();
    });



//GET CLIENT:BEGIN
app.get('/user/:email', (req, res, next) => {
    con.query('SELECT * FROM user where email=?', [req.params.email], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result[0]));
        }
        else {
            res.end(JSON.stringify("error"));
        }

    });
})

//GET list evenements
app.get('/GetEvents/', (req, res, next) => {
    con.query('SELECT * FROM evenement', function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("Nothing was found!"));
        }

    });


})

app.get('/GetComs/', (req, res, next) => {
    con.query('SELECT * FROM commentaire', function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("Nothing was found!"));
        }

    });


})

app.get('/GetShop/:id_user', (req, res, next) => {
    con.query('SELECT * FROM shop where id_user=?', [req.params.id_user], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result[0]));
        }
        else {
            res.end(JSON.stringify("error"));
        }

    });
})


//GET list articles
app.get('/GetArticle/', (req, res, next) => {
    con.query('SELECT * FROM article', function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("nothing was found"));
        }

    });


})



app.post('/evenement/add', function (req, res, next) {
    var post_data = req.body;  //Get POST params
    var nom = post_data.nom;
    var type = post_data.type;
    var dateDebut = post_data.dateDebut;
    var dateFin = post_data.dateFin;
    var distance = post_data.distance;
    var lieu = post_data.lieu;
    var infoline = post_data.infoline;
   var description = post_data.description;
    var nbPlace = post_data.nbPlace;
 var prix = post_data.prix;
    var id_user = post_data.id_user;



    con.query('INSERT INTO `evenement`(`nom`, `type`, `dateDebut`, `dateFin`, `distance`, `lieu`, `infoline`, `description`, `nbPlace`, `prix`, `id_user`) ' +
        'VALUES (?,?,?,?,?,?,?,?,?,?,?)', [nom, type, dateDebut, dateFin, distance, lieu, infoline, description, nbPlace, prix, id_user], function (err, result, fields) {
            if (err) throw err;

            res.json('Add Evenement Successful!');

        });

})



//add Participant

app.post('/participant/add/', function(req,res,next){
    var post_data = req.body;  //Get POST params
    
	var id_user = post_data.id_user;
var id_evenement= post_data.id_evenement;


 
    con.query('INSERT INTO `participants`(`id_user`,`id_evenement`) ' +
        'VALUES (?,?)',[id_user,id_evenement],function (err,result,fields) {
                if (err) throw err;

                res.json('participant ajouté avec succés');

            });

    })

app.get('/uploads/:upload', function (req, res) {
    file = req.params.upload;
    console.log(req.params.upload);
    var img = fs.readFileSync(__dirname + "/uploads/" + file);
    res.writeHead(200, { 'Content-Type': 'image/png' });
    res.end(img, 'binary');

});


//GET Events User
app.get('/GetEvenementUser/:id_user', (req, res, next) => {
    con.query('SELECT * FROM participants e JOIN evenement x ON e.id_evenement=x.id WHERE e.id_user=?', [req.params.id_user], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("error"));
        }

})});



//update event
app.put('/evenement/update/:id', function(req,res,next){
    var post_data = req.body;  //Get POST params
//var nbplace_evenement= post_data.nbplace_evenement;
//var id_evenement= post_data.id_evenement;
    con.query('UPDATE evenement SET nbPlace = nbPlace - 1 WHERE id = ?', [req.params.id],function (err,result,fields) {
                if (err) throw err;

                res.json('Evenement updated');

            });
        })


	//Delete an event
    app.delete('/particip/delete/:id', (req, res) => {
        con.query('DELETE FROM participants WHERE id_evenement = ?', [req.params.id], (err, rows, fields) => {
            if (!err)
                res.send('Deleted successfully.');
            else
                console.log(err);
        })
    });
    


// Start Server
app.listen(3000, () => {
    console.log('Fabio Restful running on port 3000');

})
