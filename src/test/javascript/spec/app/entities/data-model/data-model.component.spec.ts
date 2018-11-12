/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { DataModelComponent } from 'app/entities/data-model/data-model.component';
import { DataModelService } from 'app/entities/data-model/data-model.service';
import { DataModel } from 'app/shared/model/data-model.model';

describe('Component Tests', () => {
    describe('DataModel Management Component', () => {
        let comp: DataModelComponent;
        let fixture: ComponentFixture<DataModelComponent>;
        let service: DataModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelComponent],
                providers: []
            })
                .overrideTemplate(DataModelComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataModelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataModelService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DataModel(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dataModels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
