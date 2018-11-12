/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { DataModelDetailComponent } from 'app/entities/data-model/data-model-detail.component';
import { DataModel } from 'app/shared/model/data-model.model';

describe('Component Tests', () => {
    describe('DataModel Management Detail Component', () => {
        let comp: DataModelDetailComponent;
        let fixture: ComponentFixture<DataModelDetailComponent>;
        const route = ({ data: of({ dataModel: new DataModel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataModelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataModelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataModel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
