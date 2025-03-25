import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { PersonalRequest } from '../interface/personal-request';

@Injectable({
  providedIn: 'root'
})
export class PersonalService {

  constructor(private _http: HttpClient) { }

  getListarPersonal(token: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } })
  }

  getPersonalId(token: any, id: number): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/${id}`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
    );
  }

  registerPersonal(formData: FormData, token: any): Observable<any> {
    return this._http.post(`${environment.api}v1/admin/registro/personal`, formData, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
  }


  updatePersonal(formData: FormData, token: any, id: number): Observable<any> {
    return this._http.put(`${environment.api}v1/admin/personal/${id}`, formData, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
  }

  deletePersonal(token: any, id: number): Observable<any> {
    return this._http.delete(`${environment.api}v1/admin/personal/${id}`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
    );
  }

  fotoPersonal(token: any, personalid: number): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/image/${personalid}`,
      { 'headers': { 'Authorization': `Bearer ${token}` }, responseType: 'blob' }
    );
  }


  inePersonal(token: any, personalid: number): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/ine/${personalid}`,
      { 'headers': { 'Authorization': `Bearer ${token}` }, responseType: 'blob' }
    );
  }


  estadosPersonal(token: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/estados`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
    );
  }

  buscarEstado(token: any, id: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/estados/${id}`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
    );
  }

  horariosPersonal(token: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/horarios`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
    );
  }


  jornadaPersonal(token: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal/jornadas`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } }
    );
  }


}
