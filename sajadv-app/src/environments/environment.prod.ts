const api = '//localhost:8762/api/sajadv-service/v1';

export const environment = {
  production: true,
  pessoas: {
    save: api + '/pessoas', // POST
    findAll: api + '/pessoas', // GET
    findById: api + '/pessoas', // GET
    delete: api + '/pessoas', // DELETE
    update: api + '/pessoas' // PUT
  }
};
