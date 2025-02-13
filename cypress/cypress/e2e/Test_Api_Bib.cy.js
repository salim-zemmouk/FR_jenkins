describe('Test BibliothequeAPI', () => {
  it('should drop table from Data base ', () => {
    cy.task('queryDatabase', { query: 'DELETE FROM loan' }).then((row)=>{
      expect(row).to.be.empty;
      cy.task('queryDatabase',{query: 'DELETE FROM book'}).then((row)=>{
        expect(row).to.be.empty;
        cy.task('queryDatabase',{query: 'DELETE FROM users'}).then((row)=>{
          expect(row).to.be.empty;
        })
      })
    })
    cy.log('La base de données est mise a jours avec success');
  });
  it('Should create Users', () => {
    cy.fixture('users.json').then((users) => {
      users.forEach((user) => {
        cy.request({
          method: 'POST',
          url: 'http://localhost:8081/api/v1/users/add',
          body:
              {
                name: user.name,
                email: user.email,
              },
          headers: {
            'Content-Type': 'application/json',
          },
        })
      })
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property("id");
      expect(response.body).to.have.property("name");
      expect(response.body).to.have.property('email')
    });
  });

  it('Should add books', () => {
    cy.fixture('books.json').then((books) => {
      books.forEach((book) => {
        cy.request({
          method: 'POST',
          url: 'http://localhost:8081/api/v1/books/add',
          body: {
            title: book.title,
            author: book.author,
            publicationYear: book.publicationYear,
            available: book.available,
          },
          headers: {
            'Content-Type': 'application/json',
          },
        })
      })

    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property('id');
      expect(response.body).to.have.property('title');
      expect(response.body).to.have.property('author');
      expect(response.body).to.have.property('publicationYear');
      expect(response.body).to.have.property('available');
    })
  });

  it('should recuperate all users ', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8081/api/v1/users',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body.length).to.eq(2);
      expect(response.body[0]).to.have.property("id");
      expect(response.body[0]).to.have.property("name");
      expect(response.body[0]).to.have.property('email')
      cy.log('Le Nombre d utilisateurs est :', response.body.length);
    })
  });

  it('should recuperate All available Books', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8081/api/v1/books/available',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      expect(response.status).to.eq(200);
      cy.log('The number of books available is:', response.body.length);
      expect(response.body.slice(0, 3).every(item => item.available === true)).to.eq(true);
    })
  });

  it('should Loan Books successfully', () => {
    cy.task('queryDatabase', { query: 'SELECT id FROM users' }).then((row)=>{
      expect(row).to.not.be.empty;
      const IDus=row[1].id;
      cy.task('queryDatabase', { query: 'SELECT id FROM book' }).then((rows)=>{
        expect(rows).to.not.be.empty;
        const IDbk=rows[2].id;

        cy.request({
          method:'POST',
          url:'http://localhost:8081/api/v1/loans/borrow',
          qs:{
            userId:IDus,
            bookId:IDbk,
          },
          headers: {
            "Content-Type": "application/json",
          },
        }).then((response)=>{
          expect(response.status).to.eq(200)
          expect(response.body).to.have.property("loanDate");
        })
      })
    })
  });
  it('should return available false', () => {
    cy.task('queryDatabase', { query: 'SELECT available FROM book' }).then((rows)=> {
      expect(rows).to.not.be.empty;
      expect(rows[2].available).to.eq(false);
      cy.log(`th book is note available`);
    });
  });

  it('should recuperate loans books  ', () => {
    cy.request({
      method: 'GET',
      url: 'http://localhost:8081/api/v1/loans',
      headers: {
        "accept": "application/json"
      },
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body.length).to.eq(1);
      cy.log('Le Nombre de livres emprunter est :', response.body.length);
    })
  });

  it('Should return Book',()=>{
    cy.task('queryDatabase', { query: 'SELECT id FROM loan' }).then((rows)=> {
    expect(rows).to.be.not.empty;
    const IDloa = rows[0].id;
    cy.request({
      method:'POST',
      url:'http://localhost:8081/api/v1/loans/return',
      qs:{
        loanId:IDloa,
      },
      headers:{
        'Content-Type': 'application/json',
      },
    }).then((response)=>{
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property("id");
      expect(response.body).to.have.property("loanDate");
      expect(response.body).to.have.property("returnDate");
      const systemDate = new Date().toISOString().split('T')[0];
      expect(response.body.returnDate).to.eq(systemDate)
    })
  })
  });


  it('should find book with title ', () => {
    cy.fixture('books.json').then((books)=>{
    cy.request({
      method: 'GET',
      url: 'http://localhost:8081/api/v1/books/search',
      qs:{
        title: books.title,
      },
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      expect(response.status).to.eq(200);
    })
    })
  });

  it('should find book with author ', () => {
    cy.fixture('books.json').then((books)=> {
      cy.request({
        method: 'GET',
        url: 'http://localhost:8081/api/v1/books/search',
        qs: {
          author: books.author,
        },
        headers: {
          'Content-Type': 'application/json',
        },
      }).then((response) => {
        expect(response.status).to.eq(200);
       // expect(response.body.length).to.eq(1);
       // expect(response.body[0].title).to.eq("Book 6")
      })
    });
  });

})
