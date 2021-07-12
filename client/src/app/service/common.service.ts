import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiConfig } from 'src/environments/environment';

@Injectable({providedIn: 'root'})
export class CommonService {
  constructor(private http: HttpClient) {
  }

  /**
   * Get common method to call to api
   * @param strUrl
   * @param options
   */
  get(strUrl: string, options: any): Observable<any> {
    let params = new HttpParams();
    for (const key in options) {
      params = options[key] != null ? params.set(key, options[key]) : params;
    }
    return this.http
      .get(ApiConfig.url.concat(strUrl), {params})
      .pipe(
        catchError(this.handleError)
      )
  }

  /**
   * Post common method to call to api.
   * @param strUrl
   * @param params
   */
  post(strUrl: string, params: any): Observable<any> {
    return this.http
      .post(ApiConfig.url.concat(strUrl), params)
      .pipe(
        catchError(this.handleError)
      );
  }

  handleError(error: any) {
    const errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    //console.error(errMsg); // log to console instead
    return of(errMsg);
  }
}
