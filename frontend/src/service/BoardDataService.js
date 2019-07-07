import axios from 'axios'

class BoardDataService {
    retrieveAllPits(pit_id) {
        return axios.get(`http://localhost:8080/${pit_id}`);
    }
}

export default new BoardDataService()