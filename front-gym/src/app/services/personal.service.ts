import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PersonalService {

  constructor(private _http: HttpClient) { }

  getListarPersonal(token: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/personal`,
      { 'headers': { 'content-Type': 'application/json', 'Authorization': `Bearer ${token}` } })
  }
}
