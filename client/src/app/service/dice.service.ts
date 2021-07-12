import { Injectable } from '@angular/core';
import { HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommonService } from 'src/app/service/common.service';

@Injectable({providedIn: 'root'})
export class DiceService {
  constructor(private commonService: CommonService) {
  }

  rollDice(url: string, options: any): Observable<HttpEvent<any>> {
    return this.commonService.get(url, options);
  }

  saveAndRollDice(url: string, value: any) {
    return this.commonService.post(url, value);
  }
}
