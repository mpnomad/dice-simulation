import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { RollModel } from 'src/app/model/roll.model';
import { DiceService } from 'src/app/service/dice.service';
import { SimulationReportModel } from 'src/app/model/simulation-report.model';
import { RollReportModel } from "src/app/model/roll-report.model";

interface DiceRollResult {
  rollCount: number;
  sideCount: number;
  diceCount: number;
  rollTotal: number
}

interface DiceRollSummary {
  rollCount: number;
  diceCount: number;
  sidesCount: number;
  rollList: RollModel[]
  simulationReport: SimulationReportModel;
  rollReport: RollReportModel[];
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  diceCountSelect: any[] = [];
  diceSideSelect: any[] = [
    {value: 4, viewValue: '4'},
    {value: 6, viewValue: '6'},
    {value: 8, viewValue: '8'},
    {value: 10, viewValue: '10'},
    {value: 12, viewValue: '12'},
    {value: 20, viewValue: '20'},
    {value: 100, viewValue: '100'}
  ];

  isLoading: boolean = false;
  displayedColumns: string[] = ['rollCount', 'sideCount', 'diceCount', 'rollTotal'];
  dataSource = new MatTableDataSource<DiceRollResult>([]);
  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;

  displayedColumns2: string[] = ['rollValue', 'rollValueTotal', 'rollValueRate'];
  dataSource2 = new MatTableDataSource<RollReportModel[]>([]);

  form!: FormGroup;
  diceCount: FormControl;
  sideCount: FormControl;
  rollCount: FormControl;

  simulationReport: SimulationReportModel | undefined;

  constructor(private formBuilder: FormBuilder,
              private diceService: DiceService) {
    this.diceCount = new FormControl();
    this.sideCount = new FormControl();
    this.rollCount = new FormControl();
  }

  ngOnInit(): void {
    //this.paginator.pageSizeOptions = [10, 20, 50];
    //this.paginator.pageSize = 20;
    //this.dataSource.paginator = this.paginator;
    this.initFormValidation();
    this.populateDiceCountSelect();
  }

  rollDice() {
    this.diceService.rollDice('roll-dice', this.form.value).subscribe((result: any) => {
      console.log(result);
      if (result.success) {
        this.processDiceRollResult(result.result);
      }
    })
  }

  submit() {
    if (this.form.valid) {
      this.diceService.saveAndRollDice('save-roll-dice', this.form.value)
        .subscribe((result: any) => {
          console.log(result);
          if (result.success) {
            this.processDiceRollResult(result.result);
          }
        });
    }
  }

  /** Private methods**/
  private initFormValidation() {
    this.form = this.formBuilder.group({
      diceCount: ['', [Validators.required, Validators.min(1)]],
      sideCount: ['', [Validators.required, Validators.min(4)]],
      rollCount: ['', [Validators.required, Validators.min(1), Validators.max(1000)]],
    });
  }

  //populate fields. //TODO: make configurable in db
  private populateDiceCountSelect() {
    for (let ctr = 1; ctr <= 100; ctr++) {
      this.diceCountSelect.push({'value': ctr, 'viewValue': ctr});
    }
  }

  private processDiceRollResult(result: any) {
    let diceRollSummary: DiceRollSummary;
    let diceRollResults: DiceRollResult[] = [];
    diceRollSummary = result;
    this.simulationReport = result.simulationReport;
    diceRollSummary.rollList.forEach((i: RollModel) => {
      diceRollResults.push({
        rollCount: i.rollCount,
        sideCount: diceRollSummary.sidesCount,
        diceCount: diceRollSummary.diceCount,
        rollTotal: i.rollTotal
      });
    });
    console.log(diceRollSummary);
    this.dataSource = new MatTableDataSource<DiceRollResult>(diceRollResults);
    this.dataSource2 = result.rollReport;
    //this.paginator.length = diceRollResults.length;
  }
}
